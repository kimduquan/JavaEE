/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author FOXCONN
 */
@CacheDefaults(cacheName = "Entity")
@RequestScoped
public class Cache {
    
    @Inject
    private Application application;
    
    @PersistenceContext(name = "EPF", unitName = "EPF")
    private EntityManager defaultManager;
    
    EntityManager joinTransaction(EntityManager manager){
        if(manager != null){
            if(!manager.isJoinedToTransaction()){
                manager.joinTransaction();
            }
        }
        return manager;
    }
    
    EntityManager getManager(SecurityContext context){
        EntityManager manager;
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            Session session = application.getSession(principal.getName());
            if(session != null){
                if(principal instanceof JsonWebToken){
                    JsonWebToken jwt = (JsonWebToken)principal;
                    Conversation conversation = session.getSession(jwt.getTokenID());
                    if(conversation == null){
                        conversation = session.putSession(jwt.getTokenID());
                    }
                    manager = conversation.getManager(jwt.getIssuedAtTime());
                    if(manager == null){
                        manager = conversation.putManager(jwt.getIssuedAtTime());
                    }
                    return manager;
                }
                else{
                    manager = session.getDefaultManager();
                    return manager;
                }
            }
        }
        return defaultManager;
    }
    
    public Query createNamedQuery(SecurityContext context, String name){
        return getManager(context).createNamedQuery(name);
    }
    
    @Transactional
    @CachePut
    public void persist(
            SecurityContext context,
            @CacheKey
            String name,
            @CacheKey
            String id, 
            @CacheValue
            Object object){
        EntityManager manager = joinTransaction(getManager(context));
        manager.persist(object);
    }
    
    @CacheResult
    public Object find(SecurityContext context, @CacheKey String name, Class cls, @CacheKey String id){
        return getManager(context).find(cls, id);
    }
    
    @Transactional
    @CacheRemove
    public void remove(SecurityContext context, @CacheKey String name, @CacheKey String id, Object object){
        EntityManager manager = joinTransaction(getManager(context));
        manager.remove(object);
    }
    
    @CacheResult(cacheName = "EntityType")
    public Entity findEntity(SecurityContext context, @CacheKey String name){
        Entity result = new Entity();
        getManager(context).getMetamodel()
                .getEntities()
                .stream()
                .filter(
                        entityType -> {
                            return entityType.getName().equals(name);
                        }
                )
                .findFirst()
                .ifPresent(result::setType);
        return result;
    }
    
    @CacheResult(cacheName = "NamedQuery")
    public <T> List<T> getNamedQueryResult(SecurityContext context, @CacheKey String name, Class<T> cls){
        return getManager(context)
                .createNamedQuery(name, cls)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
