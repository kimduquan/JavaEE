package epf.persistence.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
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
	private transient Metamodel metamodel;
	/**
	 * 
	 */
	private transient CriteriaBuilder criteria;
	/**
	 * 
	 */
	private transient Entity<Object> entityObject;
	/**
	 * 
	 */
	private transient List<PathSegment> pathSegments;
	
	/**
	 *
	 */
	private transient List<String> sort;

	/**
	 * @param manager
	 * @return
	 */
	public QueryBuilder criteria(final CriteriaBuilder criteria) {
		this.criteria = criteria;
		return this;
	}
	
	public QueryBuilder metamodel(final Metamodel metamodel) {
		this.metamodel = metamodel;
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
	 * @param sort
	 * @return
	 */
	public QueryBuilder sort(final List<String> sort) {
		this.sort = sort;
		return this;
	}
	
	/**
	 * @return
	 */
	public CriteriaQuery<Object> build() {
		return build(metamodel, criteria, entityObject, pathSegments.get(0), pathSegments, sort);
	}
	
	/**
	 * @param metamodel
	 * @param builder
	 * @param entity
	 * @param rootSegment
	 * @param paths
	 * @return
	 */
	protected static CriteriaQuery<Object> build(
			final Metamodel metamodel, 
			final CriteriaBuilder criteria,
			final Entity<Object> entity, 
			final PathSegment rootSegment,
			final List<PathSegment> paths,
			final List<String> sort) {
    	final EntityType<Object> rootType = entity.getType();
    	final Class<Object> rootClass = rootType.getJavaType();
    	final CriteriaQuery<Object> rootQuery = criteria.createQuery(rootClass);
    	final Root<Object> rootFrom = rootQuery.from(rootClass);
    	final List<Predicate> allParams = new ArrayList<>();
        rootSegment.getMatrixParameters().forEach((name, values) -> {
        	allParams.add(
        			criteria.isMember(
                            values,
                            rootFrom.get(name)
                    )
            );
        });
        final Var<ManagedType<?>> parentType = new Var<>(rootType);
        final Root<?> parentFrom = rootFrom;
        final Var<Join<?,?>> parentJoin = new Var<>();
        paths.subList(1, paths.size()).forEach(
        		segment -> buildAttributeSegment(
        				segment, 
                		parentType, 
                		parentJoin, 
                		parentFrom, 
                		allParams, 
                		metamodel, 
                		criteria
                		)
        		);
        if(parentJoin.get().isEmpty()){
        	rootQuery.select(rootFrom);
        }
        else{
            rootQuery.select(parentJoin.get().get());
        }
        if(!allParams.isEmpty()){
            rootQuery.where(allParams.toArray(new Predicate[0]));
        }
        if(sort != null && !sort.isEmpty()) {
        	Path<?> path = rootFrom;
        	if(parentJoin.get().isPresent()) {
        		path = parentJoin.get().get();
        	}
        	final List<Order> orders = new ArrayList<>();
        	for(String order : sort) {
        		orders.add(criteria.asc(path.get(order)));
        	}
        	rootQuery.orderBy(orders);
        }
        return rootQuery;
	}
	
	/**
     * @param segment
     * @param parentType
     * @param parentJoin
     * @param parentFrom
     * @param allParams
     * @param manager
     */
    protected static void buildAttributeSegment(
    		final PathSegment segment, 
    		final Var<ManagedType<?>> parentType, 
    		final Var<Join<?,?>> parentJoin,
    		final Root<?> parentFrom,
    		final List<Predicate> allParams,
    		final Metamodel metamodel,
    		final CriteriaBuilder criteria) {
    	final Attribute<?,?> attribute = parentType.get().get().getAttribute(segment.getPath());
        if (attribute.getPersistentAttributeType() != PersistentAttributeType.BASIC) {
            Class<?> subClass = null;
            if(attribute.isCollection()){
                if(attribute.getJavaType() == List.class){
                    subClass = parentType.get().get().getList(segment.getPath()).getBindableJavaType();
                }
                else if(attribute.getJavaType() == Map.class){
                    subClass = parentType.get().get().getMap(segment.getPath()).getBindableJavaType();
                }
                else if(attribute.getJavaType() == Set.class){
                    subClass = parentType.get().get().getSet(segment.getPath()).getBindableJavaType();
                }
                else if(attribute.getJavaType() == Collection.class){
                    subClass = parentType.get().get().getCollection(segment.getPath()).getBindableJavaType();
                }
            }
            else if(attribute.isAssociation()){
                subClass = parentType.get().get().getSingularAttribute(segment.getPath()).getBindableJavaType();
            }
            else {
            	subClass = parentType.get().get().getSingularAttribute(segment.getPath()).getJavaType();
            }
            
            Join<?,?> subJoin;
            if(parentJoin.get().isEmpty()){
                subJoin = parentFrom.join(segment.getPath());
            }
            else{
                subJoin = parentJoin.get().get().join(segment.getPath());
            }

            segment.getMatrixParameters().forEach((name, values) -> {
            	allParams.add(
            			criteria.isMember(
                                values,
                                subJoin.get(name)
                        )
                );
            });
            
            final ManagedType<?> subType = metamodel.managedType(subClass);
            parentType.set(subType);
            parentJoin.set(subJoin);
        }
    }
}
