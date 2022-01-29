package epf.persistence;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.opentracing.Traced;
import epf.persistence.internal.Application;
import epf.persistence.internal.Embeddable;
import epf.persistence.internal.Entity;
import epf.persistence.internal.Session;
import epf.persistence.util.EntityManagerUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Traced
public class Request {
    
    /**
     * 
     */
    @Inject
    private transient Application application;
    

    
    /**
     * @return
     */
    public Session getSession(final SecurityContext context) {
    	final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    	return application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new);
    }
    
    /**
     * @param session
     * @param name
     * @return
     */
    public EntityType<?> getEntityType(final Session session, final String name){
    	return session.peekManager(
    			entityManager -> {
    				return entityManager
    						.getMetamodel()
    						.getEntities()
    						.stream()
    						.filter(type -> type.getName().equals(name))
    						.findFirst()
    						.orElseThrow(NotFoundException::new);
    				}
    			)
    			.orElseThrow(NotFoundException::new);
    }
    
    /**
     * @param session
     * @param entityType
     * @param id
     * @return
     */
    public Object getEntity(final Session session, final EntityType<?> entityType, final Object id) {
    	return session
    			.peekManager(entityManager -> entityManager.find(entityType.getJavaType(), id))
    			.orElseThrow(NotFoundException::new);
    }
    
    /**
     * @param entityType
     * @param id
     * @return
     */
    public Object getEntityId(final EntityType<?> entityType, final String id) {
    	Object entityId = id;
    	try {
	    	switch(entityType.getIdType().getJavaType().getName()) {
	    		case "java.lang.Integer":
	    			entityId = Integer.valueOf(id);
	    			break;
	    		case "java.lang.Long":
	    			entityId = Long.valueOf(id);
	    			break;
	    		default:
	    			break;
	    	}
    	}
    	catch(NumberFormatException ex) {
    		throw new BadRequestException(ex);
    	}
    	return entityId;
    }
    
    /**
     * @param session
     * @param entityType
     * @param id
     * @return
     */
    @Transactional
    public Optional<Object> removeEntity(final Session session, final EntityType<?> entityType, final Object id) {
    	return session.peekManager(entityManager -> {
    		entityManager = EntityManagerUtil.joinTransaction(entityManager);
    		final Object entity = entityManager.find(entityType.getJavaType(), id);
    		if(entity != null) {
    			entityManager.remove(entity);
    			entityManager.flush();
    		}
			return entity;
    	});
    }
    
    /**
     * @param session
     * @param entity
     */
    @Transactional
    public void mergeEntity(final Session session, final Object entity) {
    	session.peekManager(entityManager -> {
    		entityManager = EntityManagerUtil.joinTransaction(entityManager);
        	final Object object = entityManager.merge(entity);
        	entityManager.flush();
        	return object;
        	}
    	);
    }
    

    
    /**
     * @param session
     * @param entity
     */
    @Transactional
    public Object persistEntity(final Session session, final Object entity) {
    	return session
    			.peekManager(entityManager -> {
    				entityManager = EntityManagerUtil.joinTransaction(entityManager);
		        	entityManager.persist(entity);
		        	entityManager.flush();
		        	return entity;
		        	}
    			)
    			.get();
    }
    
    /**
     * @param <T>
     * @param session
     * @return
     */
    public <T> Stream<Entity<T>> getEntities(final Session session) {
    	return session.peekManager(
    			entityManager -> {
    				return entityManager
    						.getMetamodel()
		        			.getEntities()
		        			.stream()
		        			.map(entityType -> { 
		        					Entity<T> entity = null;
		        					try {
		                        		@SuppressWarnings("unchecked")
		                        		final EntityType<T> type = entityType.getClass().cast(entityType);
		                        		entity = new Entity<>();
		                        		entity.setType(type);
		        	                	}
		                        	catch(Exception ex) {
		                        		throw ex;
		                        	}
		        					return entity;
		        				}
		        			)
		        			.filter(entity -> entity != null);
    				}
    			)
    			.get();
    }
    
    /**
     * @param <T>
     * @param session
     * @return
     */
    public <T> Stream<Embeddable<T>> getEmbeddables(final Session session){
    	return session.peekManager(entityManager -> {
    		return entityManager
        			.getMetamodel()
        			.getEmbeddables()
        			.stream()
        			.map(embeddableType -> { 
        					Embeddable<T> embeddable = null;
        					try {
                        		@SuppressWarnings("unchecked")
                        		final EmbeddableType<T> type = embeddableType.getClass().cast(embeddableType);
                        		embeddable = new Embeddable<>();
                        		embeddable.setType(type);
        	                	}
                        	catch(Exception ex) {
                        		throw ex;
                        	}
        					return embeddable;
        				}
        			)
        			.filter(embeddable -> embeddable != null);
    	})
    	.get();
    }
    
    /**
     * @param <T>
     * @param session
     * @param name
     * @param cls
     * @param firstResult
     * @param maxResults
     * @return
     */
    public <T> Stream<T> getNamedQueryResult(
    		final Session session,
    		final String name, 
    		final Class<T> cls, 
    		final Optional<Integer> firstResult, 
    		final Optional<Integer> maxResults) {
    	return session
    			.peekManager(
    					entityManager -> {
    						TypedQuery<T> query = entityManager.createNamedQuery(name, cls);
    				    	if(firstResult.isPresent()) {
    				    		query = query.setFirstResult(firstResult.get());
    				    	}
    				    	if(maxResults.isPresent()) {
    				    		query = query.setMaxResults(maxResults.get());
    				    	}
    				    	return query.getResultStream();
    					})
    			.get();
    }
    
    /**
     * @param session
     * @param entityTables
     * @param entityAttributes
     */
    public void mapEntities(
    		final Session session,
    		final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
    	session.peekManager(entityManager -> {
        	entityManager
        	.getMetamodel()
        	.getEntities().forEach(entityType -> {
    			String tableName = entityType.getName().toLowerCase(Locale.getDefault());
    			final Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
    			if(tableAnnotation != null) {
    				tableName = tableAnnotation.name();
    			}
    			entityTables.put(tableName, entityType);
    			final Map<String, Attribute<?,?>> attributes = new ConcurrentHashMap<>();
    			entityType.getAttributes().forEach(attr -> {
    				String columnName = attr.getName().toLowerCase(Locale.getDefault());
    				if(attr.getJavaMember() instanceof Field) {
    					final Field field = (Field) attr.getJavaMember();
    					final Column columnAnnotation = field.getAnnotation(Column.class);
    					if(columnAnnotation != null) {
    						columnName = columnAnnotation.name();
    					}
    				}
    				attributes.put(columnName, attr);
    			});
    			entityAttributes.put(tableName, attributes);
    		});
        	return null;
    	});
    }
}
