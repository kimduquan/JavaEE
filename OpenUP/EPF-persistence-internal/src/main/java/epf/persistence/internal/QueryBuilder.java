package epf.persistence.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.Attribute.PersistentAttributeType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.PathSegment;
import epf.naming.Naming;
import epf.util.Var;

public class QueryBuilder {

	private transient Metamodel metamodel;
	private transient CriteriaBuilder criteria;
	private transient Entity<Object> entityObject;
	private transient List<PathSegment> pathSegments;
	private transient List<String> sort;
	private transient boolean countOnly = false;

	public QueryBuilder criteria(final CriteriaBuilder criteria) {
		this.criteria = criteria;
		return this;
	}
	
	public QueryBuilder metamodel(final Metamodel metamodel) {
		this.metamodel = metamodel;
		return this;
	}

	public QueryBuilder entity(final Entity<Object> entity) {
		this.entityObject = entity;
		return this;
	}

	public QueryBuilder paths(final List<PathSegment> paths) {
		this.pathSegments = paths;
		return this;
	}
	
	public QueryBuilder sort(final List<String> sort) {
		this.sort = sort;
		return this;
	}
	
	public QueryBuilder countOnly() {
		this.countOnly = true;
		return this;
	}
	
	public CriteriaQuery<Object> build() {
		return build(metamodel, criteria, entityObject, pathSegments.get(0), pathSegments, sort, countOnly);
	}
	
	protected static CriteriaQuery<Object> build(
			final Metamodel metamodel, 
			final CriteriaBuilder criteria,
			final Entity<Object> entity, 
			final PathSegment rootSegment,
			final List<PathSegment> paths,
			final List<String> sort,
			final boolean countOnly) {
    	final EntityType<Object> rootType = entity.getType();
    	final Class<Object> rootClass = rootType.getJavaType();
    	final CriteriaQuery<Object> rootQuery = countOnly ? criteria.createQuery() : criteria.createQuery(rootClass);
    	final Root<Object> rootFrom = rootQuery.from(rootClass);
    	final List<Predicate> allParams = new ArrayList<>();
    	final MultivaluedMap<String, String> matrixParams = rootSegment.getMatrixParameters();
    	matrixParams.forEach((name, values) -> {
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
        if(!parentJoin.get().isPresent()){
        	if(countOnly) {
            	rootQuery.select(criteria.count(rootFrom));
        	}
        	else {
            	rootQuery.select(rootFrom);
        	}
        }
        else{
        	if(countOnly) {
            	rootQuery.select(criteria.count(parentJoin.get().get()));
        	}
        	else {
            	rootQuery.select(parentJoin.get().get());
        	}
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
        		final int sepIndex = order.indexOf(Naming.Query.Client.PARAM_SEPARATOR);
        		if(sepIndex > 0) {
        			final String sortAttr = order.substring(0, sepIndex);
        			final String sortOrder = order.substring(sepIndex + 1);
        			if(Naming.Query.Client.SORT_ASC.equals(sortOrder)) {
                		orders.add(criteria.asc(path.get(sortAttr)));
        			}
        			else if(Naming.Query.Client.SORT_DESC.equals(sortOrder)) {
        				orders.add(criteria.desc(path.get(sortAttr)));
        			}
        		}
        		else {
        			orders.add(criteria.asc(path.get(order)));
        		}
        	}
        	rootQuery.orderBy(orders);
        }
        return rootQuery;
	}
	
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
            if(!parentJoin.get().isPresent()){
                subJoin = parentFrom.join(segment.getPath());
            }
            else{
                subJoin = parentJoin.get().get().join(segment.getPath());
            }

            segment.getMatrixParameters().forEach((name, values) -> {
            	final int sepIndex = name.indexOf(Naming.Query.Client.PARAM_SEPARATOR);
            	if(sepIndex > 0) {
            		final String filterAttr = name.substring(sepIndex + 1);
            		final String filterCriteria = name.substring(0, sepIndex);
            		switch(filterCriteria) {
            			case "ge":
            				allParams.add(criteria.ge(subJoin.get(filterAttr), Double.valueOf(values.get(0))));
            				break;
            			case "gt":
            				allParams.add(criteria.gt(subJoin.get(filterAttr), Double.valueOf(values.get(0))));
            				break;
            			case "le":
            				allParams.add(criteria.le(subJoin.get(filterAttr), Double.valueOf(values.get(0))));
            				break;
            			case "lt":
            				allParams.add(criteria.lt(subJoin.get(filterAttr), Double.valueOf(values.get(0))));
            				break;
            			case "not":
            				allParams.add(criteria.isNotMember(values, subJoin.get(filterAttr)));
            				break;
            			case Naming.Query.Client.LIKE:
            				allParams.add(criteria.like(subJoin.get(filterAttr), values.get(0)));
            				break;
            			case "notlike":
            				allParams.add(criteria.notLike(subJoin.get(filterAttr), values.get(0)));
            				break;
            			default:
            				break;
            		}
            	}
            	else {
                	allParams.add(
                			criteria.isMember(
                                    values,
                                    subJoin.get(name)
                            )
                    );
            	}
            });
            
            final ManagedType<?> subType = metamodel.managedType(subClass);
            parentType.set(subType);
            parentJoin.set(subJoin);
        }
    }
}
