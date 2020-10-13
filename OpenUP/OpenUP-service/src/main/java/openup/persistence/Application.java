/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    @PersistenceContext(name = "EPF", unitName = "EPF")
    private EntityManager defaultManager;
    
    @Inject
    private Event<EntityManager> event;
    
    private Map<String, EntityManagerFactory> factories;
    private Map<String, EntityManager> managers;
    
    @PostConstruct
    void postConstruct(){
        factories = new ConcurrentHashMap<>();
        managers = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        managers.values().forEach(manager -> {
            manager.close();
        });
        managers.clear();
        factories.values().forEach(factory -> {
            factory.close();
        });
        factories.clear();
    }
    
    public boolean createFactory(String userName, String password){
        boolean hasExist = factories.containsKey(userName) && managers.containsKey(userName);
    	if(!hasExist){
            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.jdbc.user", userName);
            props.put("javax.persistence.jdbc.password", password);            
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("OpenUP", props);
            EntityManager manager = factory.createEntityManager();
            managers.put(userName, manager);
            factories.put(userName, factory);
            event.fire(manager);
        }
        return !hasExist;
    }
    
    public EntityManagerFactory getFactory(Principal principal){
        return factories.get(principal.getName());
    }
    
    public EntityManager getDefaultManager(){
        return defaultManager;
    }
}
