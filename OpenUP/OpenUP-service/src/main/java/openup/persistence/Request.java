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
import javax.security.enterprise.AuthenticationException;
import javax.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author FOXCONN
 */
@CacheDefaults(cacheName = "Entity")
@RequestScoped
public class Request {
    
    @PersistenceContext(name = "EPF", unitName = "EPF")
    private EntityManager defaultManager;
    
    @Inject
    private Application application;
    
    EntityManager joinTransaction(EntityManager manager){
        if(manager != null){
            if(!manager.isJoinedToTransaction()){
                manager.joinTransaction();
            }
        }
        return manager;
    }
    
    EntityManager getManager(String unit, Principal principal) throws Exception{
        if(principal != null){
            EntityManager manager = null;
            Context context = application.getContext(unit);
            if(context != null){
                Credential credential = context.getCredential(principal.getName());
                if(credential != null){
                    Session session = credential.getSession(principal);
                    if(session != null){
                        if(principal instanceof JsonWebToken){
                            JsonWebToken jwt = (JsonWebToken)principal;
                            Conversation conversation = session.getConversation(jwt.getTokenID());
                            if(conversation != null){
                                manager = conversation.getManager(jwt.getIssuedAtTime());
                            }
                        }
                    }
                }
            }
            if(manager == null){
                throw new AuthenticationException();
            }
            return manager;
        }
        return defaultManager;
    }
    
    public Query createNamedQuery(String unit, Principal principal, String name) throws Exception{
        return getManager(unit, principal).createNamedQuery(name);
    }
    
    @Transactional
    @CachePut
    public void persist(
            @CacheKey
            String unit,
            Principal principal,
            @CacheKey
            String name,
            @CacheKey
            String id, 
            @CacheValue
            Object object) throws Exception{
        EntityManager manager = getManager(unit, principal);
        manager = joinTransaction(manager);
        manager.persist(object);
    }
    
    @CacheResult
    public Object find(@CacheKey String unit, Principal principal, @CacheKey String name, Class cls, @CacheKey String id) throws Exception{
        return getManager(unit, principal).find(cls, id);
    }
    
    @Transactional
    @CacheRemove
    public void remove(@CacheKey String unit, Principal principal, @CacheKey String name, @CacheKey String id, Object object) throws Exception{
        EntityManager manager = getManager(unit, principal);
        manager = joinTransaction(manager);
        manager.remove(object);
    }
    
    @CacheResult(cacheName = "EntityType")
    public Entity findEntity(@CacheKey String unit, Principal principal, @CacheKey String name) throws Exception{
        Entity result = new Entity();
        getManager(unit, principal).getMetamodel()
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
    public <T> List<T> getNamedQueryResult(@CacheKey String unit, Principal principal, @CacheKey String name, Class<T> cls) throws Exception{
        return getManager(unit, principal)
                .createNamedQuery(name, cls)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
