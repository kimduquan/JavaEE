/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.ws.rs.ForbiddenException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.persistence.context.Application;
import epf.persistence.context.Context;
import epf.persistence.context.Credential;
import epf.persistence.context.Session;
import epf.persistence.impl.Entity;

/**
 *
 * @author FOXCONN
 */
@CacheDefaults(cacheName = "Entity")
@RequestScoped
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
     * @param manager
     * @return
     */
    protected EntityManager joinTransaction(final EntityManager manager){
        if(manager != null && !manager.isJoinedToTransaction()){
        	manager.joinTransaction();
        }
        return manager;
    }
    
    /**
     * @param principal
     * @return
     */
    protected EntityManager getManager(final Principal principal){
        JsonWebToken jwt = null;
        Session session = null;
        final Context context = application.getContext();
        if(principal != null && context != null){
        	final Credential credential = context.getCredential(principal.getName());
            if(credential != null && principal instanceof JsonWebToken){
            	jwt = (JsonWebToken)principal;
            	session = credential.getSession(jwt.getTokenID());
            }
        }
        return getEntityManager(principal, jwt, session);
    }
    
    /**
     * @param principal
     * @param jwt
     * @param session
     * @return
     */
    protected static EntityManager getEntityManager(final Principal principal, final JsonWebToken jwt, final Session session) {
    	EntityManager manager = null;
    	if(session != null && jwt != null && session.checkExpirationTime(jwt.getExpirationTime())){
        	manager = session
                    .putConversation(jwt.getTokenID())
                    .putManager(jwt.getIssuedAtTime());
        }
        if(principal != null && manager == null){
            throw new ForbiddenException();
        }
    	return manager;
    }
    
    /**
     * @param <T>
     * @param principal
     * @param name
     * @param cls
     * @return
     */
    public <T> TypedQuery<T> createNamedQuery(final Principal principal, final String name, final Class<T> cls) {
        return getManager(principal).createNamedQuery(name, cls);
    }
    
    /**
     * @param principal
     * @param name
     * @param object
     * @return
     */
    @Transactional
    @CacheResult
    public Object persist(
            final  Principal principal,
            @CacheKey
            final String name,
            final Object object) {
        EntityManager manager = getManager(principal);
        manager = joinTransaction(manager);
        manager.persist(object);
        manager.flush();
        return object;
    }
    
    /**
     * @param principal
     * @param name
     * @param entityId
     * @param object
     */
    @Transactional
    @CachePut
    public void merge(
            final Principal principal,
            @CacheKey
            final String name,
            @CacheKey
            final String entityId,
            @CacheValue
            final Object object) {
        EntityManager manager = getManager(principal);
        manager = joinTransaction(manager);
        manager.merge(object);
        manager.flush();
    }
    
    /**
     * @param <T>
     * @param principal
     * @param name
     * @param cls
     * @param entityId
     * @return
     */
    @CacheResult
    public <T> T find(
    		final Principal principal, 
    		@CacheKey final String name, 
    		final Class<T> cls, 
    		@CacheKey final String entityId) {
    	final EntityManager manager = getManager(principal);
    	final T object = manager.find(cls, entityId);
    	manager.refresh(object);
        return object;
    }
    
    /**
     * @param principal
     * @param name
     * @param entityId
     * @param object
     */
    @Transactional
    @CacheRemove
    public void remove(
    		final Principal principal, 
    		@CacheKey final String name, 
    		@CacheKey final String entityId, 
    		final Object object) {
        EntityManager manager = getManager(principal);
        manager = joinTransaction(manager);
        manager.remove(object);
        manager.flush();
    }
    
    /**
     * @param <T>
     * @param principal
     * @param name
     * @return
     */
    @CacheResult(cacheName = "EntityType")
    public <T> Entity<T> findEntity(final Principal principal, @CacheKey final String name) {
    	final Entity<T> result = new Entity<>();
        getManager(principal).getMetamodel()
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
     * @return
     */
    @CacheResult(cacheName = "EntityType")
    public <T> List<Entity<T>> findEntities(final Principal principal) {
    	return getManager(principal)
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
     * @param name
     * @param cls
     * @return
     */
    @CacheResult(cacheName = "NamedQuery")
    public <T> List<T> getNamedQueryResult(
    		final Principal principal, 
    		@CacheKey final String name, 
    		final Class<T> cls) {
        return getManager(principal)
                .createNamedQuery(name, cls)
                .getResultStream()
                .collect(Collectors.toList());
    }
    
    /**
     * @param <T>
     * @param principal
     * @param name
     * @param cls
     * @param firstResult
     * @param maxResults
     * @return
     */
    @CacheResult(cacheName = "NamedQuery")
    public <T> List<T> getNamedQueryResult(
    		final Principal principal, 
    		@CacheKey final String name, 
    		final Class<T> cls, 
    		final Integer firstResult, 
    		final Integer maxResults) {
        return getManager(principal)
                .createNamedQuery(name, cls)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultStream()
                .collect(Collectors.toList());
    }
    
    /**
     * @param principal
     * @param entityTables
     * @param entityAttributes
     */
    public void mapEntities(
    		final Principal principal, 
    		final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
    	final EntityManager manager = getManager(principal);
    	manager.getMetamodel().getEntities().forEach(entityType -> {
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
}
