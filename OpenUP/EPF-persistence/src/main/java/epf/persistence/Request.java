/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.ws.rs.ForbiddenException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.opentracing.Traced;
import epf.persistence.internal.Application;
import epf.persistence.internal.Embeddable;
import epf.persistence.internal.Entity;
import epf.persistence.util.EntityManagerUtil;

/**
 *
 * @author FOXCONN
 */
@CacheDefaults(cacheName = "Entity")
@RequestScoped
@Traced
public class Request {
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
    
    /**
     * 
     */
    @Inject
    private transient Application application;
    
    /**
     * @param principal
     * @param schema
     * @param name
     * @param cls
     */
    public <T> TypedQuery<T> createNamedQuery(final JsonWebToken jwt, final String schema, final String name, final Class<T> cls) {
    	return application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
    			.peekManager(entityManager -> entityManager.createNamedQuery(name, cls))
    			.get();
    }
    
    /**
     * @param principal
     * @param schema
     * @param name
     * @param object
     */
    @Transactional
    @CacheResult
    public Object persist(
            final JsonWebToken jwt,
            @CacheKey
            final String schema,
            @CacheKey
            final String name,
            final Object object) {
    	application
    	.getSession(jwt)
    	.orElseThrow(ForbiddenException::new)
    	.peekManager(entityManager -> {
    		entityManager = EntityManagerUtil.joinTransaction(entityManager);
        	entityManager.persist(object);
        	entityManager.flush();
        	return object;
        });
        return object;
    }
    
    /**
     * @param principal
     * @param schema
     * @param name
     * @param entityId
     * @param object
     */
    @Transactional
    @CachePut
    public void merge(
            final JsonWebToken jwt,
            @CacheKey
            final String schema,
            @CacheKey
            final String name,
            @CacheKey
            final String entityId,
            @CacheValue
            final Object object) {
    	application
    	.getSession(jwt)
    	.orElseThrow(ForbiddenException::new)
    	.peekManager(entityManager -> {
        	entityManager = EntityManagerUtil.joinTransaction(entityManager);
        	entityManager.merge(object);
        	entityManager.flush();
            return object;
        });
    }
    
    /**
     * @param principal
     * @param schema
     * @param name
     * @param cls
     * @param entityId
     */
    @CacheResult
    public <T> T find(
    		final JsonWebToken jwt,
    		@CacheKey
            final String schema,
    		@CacheKey 
    		final String name, 
    		final Class<T> cls, 
    		@CacheKey 
    		final String entityId) {
    	return application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
    			.peekManager(entityManager -> {
    				final T object = entityManager.find(cls, entityId);
    				return object;
    				}
    			)
    			.orElse(null);
    }
    
    /**
     * @param principal
     * @param schema
     * @param name
     * @param entityId
     * @param object
     */
    @Transactional
    @CacheRemove
    public void remove(
    		final JsonWebToken jwt, 
    		@CacheKey
            final String schema,
    		@CacheKey 
    		final String name, 
    		@CacheKey 
    		final String entityId, 
    		final Object object) {
    	application
    	.getSession(jwt)
    	.orElseThrow(ForbiddenException::new)
    	.peekManager(entityManager -> {
        	entityManager = EntityManagerUtil.joinTransaction(entityManager);
        	entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
        	entityManager.flush();
        	return object;
        });
    }
    
    /**
     * @param <T>
     * @param manager
     * @param schema
     * @param name
     * @return
     */
    protected <T> Entity<T> findFirstEntity(final EntityManager manager, final String schema, final String name){
    	final Entity<T> result = new Entity<>();
    	manager.getMetamodel()
        .getEntities()
        .stream()
        .filter(
                entityType -> {
                    return entityType.getName().equals(name);
                }
        )
        .findFirst()
        .ifPresent(entityType -> {
        	try {
        		@SuppressWarnings("unchecked")
        		final EntityType<T> type = entityType.getClass().cast(entityType);
            	result.setType(type);
            	}
        	catch(ClassCastException ex) {
        		logger.throwing(EntityType.class.getName(), "getClass", ex);
        	}});
    	return result;
    }
    
    /**
     * @param <T>
     * @param principal
     * @param schema
     * @param name
     * @return
     */
    @CacheResult(cacheName = "EntityType")
    public <T> Entity<T> findEntity(
    		final JsonWebToken jwt,
    		@CacheKey
            final String schema,
    		@CacheKey 
    		final String name) {
    	final Optional<Entity<T>> entity = application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
    			.peekManager(entityManager -> findFirstEntity(entityManager, schema, name));
        return entity.orElse(new Entity<>());
    }
    
    /**
     * @param <T>
     * @param entityManager
     * @return
     */
    protected <T> List<Entity<T>> collectEntities(final EntityManager entityManager){
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
                    	catch(ClassCastException ex) {
                    		logger.throwing(EntityType.class.getName(), "getClass", ex);
                    	}
    					return entity;
    				}
    			)
    			.filter(entity -> entity != null)
    			.collect(Collectors.toList());
    }
    
    /**
     * @param <T>
     * @param principal
     * @return
     */
    @CacheResult(cacheName = "EntityType")
    public <T> List<Entity<T>> findEntities(final JsonWebToken jwt) {
    	final Optional<List<Entity<T>>> entities = application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
    			.peekManager(entityManager -> collectEntities(entityManager));
    	return entities.get();
    }
    
    /**
     * @param <T>
     * @param entityManager
     * @return
     */
    protected <T> List<Embeddable<T>> collectEmbeddables(final EntityManager entityManager){
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
                    	catch(ClassCastException ex) {
                    		logger.throwing(EmbeddableType.class.getName(), "getClass", ex);
                    	}
    					return embeddable;
    				}
    			)
    			.filter(embeddable -> embeddable != null)
    			.collect(Collectors.toList());
    }
    
    /**
     * @param <T>
     * @param principal
     * @return
     */
    @CacheResult(cacheName = "EmbeddableType")
    public <T> List<Embeddable<T>> findEmbeddables(
    		final JsonWebToken jwt) {
    	final Optional<List<Embeddable<T>>> embeddables = application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
    			.peekManager(entityManager -> this.collectEmbeddables(entityManager));
    	return embeddables.get();
    }
    
    /**
     * @param <T>
     * @param entityManager
     * @param name
     * @param cls
     * @return
     */
    protected <T> List<T> collectNamedQueryResult(
    		final EntityManager entityManager, 
    		final String name, 
    		final Class<T> cls){
    	return entityManager
    			.createNamedQuery(name, cls)
                .getResultStream()
                .collect(Collectors.toList());
    }
    
    /**
     * @param <T>
     * @param entityManager
     * @param name
     * @param cls
     * @param firstResult
     * @param maxResults
     * @return
     */
    protected <T> List<T> collectNamedQueryResult(
    		final EntityManager entityManager, 
    		final String name, 
    		final Class<T> cls, 
    		final Optional<Integer> firstResult, 
    		final Optional<Integer> maxResults){
    	TypedQuery<T> query = entityManager.createNamedQuery(name, cls);
    	if(firstResult.isPresent()) {
    		query = query.setFirstResult(firstResult.get());
    	}
    	if(maxResults.isPresent()) {
    		query = query.setMaxResults(maxResults.get());
    	}
    	return query.getResultStream().collect(Collectors.toList());
    }
    
    @CacheResult(cacheName = "NamedQuery")
    public <T> List<T> getNamedQueryResult(
    		final JsonWebToken jwt, 
    		@CacheKey
            final String schema,
    		@CacheKey 
    		final String name, 
    		final Class<T> cls) {
    	return application
    			.getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
    			.peekManager(entityManager -> collectNamedQueryResult(entityManager, name, cls))
    			.get();
    }
    
    /**
     * @param principal
     * @param schema
     * @param name
     * @param cls
     * @param firstResult
     * @param maxResults
     */
    @CacheResult(cacheName = "NamedQuery")
    public <T> List<T> getNamedQueryResult(
    		final JsonWebToken jwt, 
    		@CacheKey
            final String schema,
    		@CacheKey 
    		final String name, 
    		final Class<T> cls, 
    		final Integer firstResult, 
    		final Integer maxResults) {
    	return application.
    			getSession(jwt)
    			.orElseThrow(ForbiddenException::new)
        		.peekManager(entityManager -> collectNamedQueryResult(
        				entityManager,
        				name,
        				cls,
        				Optional.ofNullable(firstResult),
        				Optional.ofNullable(maxResults)))
        		.get();
    }
    
    /**
     * @param entityManager
     * @param entityTables
     * @param entityAttributes
     */
    protected void mapEntities(
    		final EntityManager entityManager,
    		final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
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
    }
    
    /**
     * @param principal
     * @param entityTables
     * @param entityAttributes
     */
    public void mapEntities(
    		final JsonWebToken jwt,
    		final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
    	application
    	.getSession(jwt)
    	.orElseThrow(ForbiddenException::new)
    	.peekManager(entityManager -> {
    		mapEntities(entityManager, entityTables, entityAttributes);
    		return null;
    		}
    	);
    }
}
