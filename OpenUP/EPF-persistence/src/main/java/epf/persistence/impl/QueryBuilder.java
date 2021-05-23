/**
 * 
 */
package epf.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.ws.rs.core.PathSegment;
import epf.util.Var;

/**
 * @author PC
 *
 */
public class QueryBuilder {

	/**
	 * 
	 */
	private transient EntityManager entityManager;
	/**
	 * 
	 */
	private transient Entity<Object> entityObject;
	/**
	 * 
	 */
	private transient List<PathSegment> pathSegments;

	/**
	 * @param manager
	 * @return
	 */
	public QueryBuilder manager(final EntityManager manager) {
		this.entityManager = manager;
		return this;
	}

	/**
	 * @param entity
	 * @return
	 */
	public QueryBuilder entity(final Entity<Object> entity) {
		this.entityObject = entity;
		return this;
	}

	/**
	 * @param paths
	 * @return
	 */
	public QueryBuilder paths(final List<PathSegment> paths) {
		this.pathSegments = paths;
		return this;
	}
	
	/**
	 * @return
	 */
	public Query build() {
		return build(entityManager, entityObject, pathSegments.get(0), pathSegments);
	}
	
	/**
	 * @param manager
	 * @param entity
	 * @param rootSegment
	 * @param paths
	 * @return
	 */
	protected static Query build(
			final EntityManager manager, 
			final Entity<Object> entity, 
			final PathSegment rootSegment,
			final List<PathSegment> paths) {
    	final CriteriaBuilder builder = manager.getCriteriaBuilder();
    	final EntityType<Object> rootType = entity.getType();
    	final Class<Object> rootClass = rootType.getJavaType();
    	final CriteriaQuery<Object> rootQuery = builder.createQuery(rootClass);
    	final Root<Object> rootFrom = rootQuery.from(rootClass);
    	final List<Predicate> allParams = new ArrayList<>();
        rootSegment.getMatrixParameters().forEach((name, values) -> {
        	allParams.add(
                    builder.isMember(
                            values,
                            rootFrom.get(name)
                    )
            );
        });
        final Var<ManagedType<?>> parentType = new Var<>(rootType);
        final Root<?> parentFrom = rootFrom;
        final Var<Join<?,?>> parentJoin = new Var<>();
        paths.subList(1, paths.size()).forEach(
        		segment -> buildSegment(
        				segment, 
                		parentType, 
                		parentJoin, 
                		parentFrom, 
                		allParams, 
                		manager, 
                		builder
                		)
        		);
        if(parentJoin.get() == null){
        	rootQuery.select(rootFrom);
        }
        else{
            rootQuery.select(parentJoin.get());
        }
        if(!allParams.isEmpty()){
            rootQuery.where(allParams.toArray(new Predicate[0]));
        }
        return manager.createQuery(rootQuery);
	}
	
	/**
     * @param segment
     * @param parentType
     * @param parentJoin
     * @param parentFrom
     * @param allParams
     * @param manager
     */
    protected static void buildSegment(
    		final PathSegment segment, 
    		final Var<ManagedType<?>> parentType, 
    		final Var<Join<?,?>> parentJoin,
    		final Root<?> parentFrom,
    		final List<Predicate> allParams,
    		final EntityManager manager,
    		final CriteriaBuilder builder) {
    	final Attribute<?,?> attribute = parentType.get().getAttribute(segment.getPath());
        if (attribute.getPersistentAttributeType() != PersistentAttributeType.BASIC) {
            Class<?> subClass = null;
            if(attribute.isCollection()){
                if(attribute.getJavaType() == List.class){
                    subClass = parentType.get().getList(segment.getPath()).getBindableJavaType();
                }
                else if(attribute.getJavaType() == Map.class){
                    subClass = parentType.get().getMap(segment.getPath()).getBindableJavaType();
                }
                else if(attribute.getJavaType() == Set.class){
                    subClass = parentType.get().getSet(segment.getPath()).getBindableJavaType();
                }
                else if(attribute.getJavaType() == Collection.class){
                    subClass = parentType.get().getCollection(segment.getPath()).getBindableJavaType();
                }
            }
            else if(attribute.isAssociation()){
                subClass = parentType.get().getSingularAttribute(segment.getPath()).getBindableJavaType();
            }
            
            Join<?,?> subJoin;
            if(parentJoin.get() == null){
                subJoin = parentFrom.join(segment.getPath());
            }
            else{
                subJoin = parentJoin.get().join(segment.getPath());
            }

            segment.getMatrixParameters().forEach((name, values) -> {
            	allParams.add(
                        builder.isMember(
                                values,
                                subJoin.get(name)
                        )
                );
            });
            
            final ManagedType<?> subType = manager.getMetamodel().managedType(subClass);
            parentType.set(subType);
            parentJoin.set(subJoin);
        }
    }
}
