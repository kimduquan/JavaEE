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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    @PersistenceUnit(name = "OpenUP", unitName = "OpenUP")
    private EntityManagerFactory defaultFactory;
    
    @PersistenceContext(name = "OpenUP", unitName = "OpenUP")
    private EntityManager defaultManager;
    
    private Map<String, EntityManagerFactory> factories;
    private Map<String, EntityManager> managers;
    
    @PostConstruct
    void postConstruct(){
        factories = new ConcurrentHashMap();
        managers = new ConcurrentHashMap();
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
    
    public void createFactory(String userName, String password){
        if(managers.containsKey(userName) == false){
            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.jdbc.user", userName);
            props.put("javax.persistence.jdbc.password", password);            
            if(factories.containsKey(userName) == false){
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("OpenUP-service", props);
                EntityManager manager = factory.createEntityManager();
                managers.put(userName, manager);
                factories.put(userName, factory);
            }
        }
    }
    
    public EntityManagerFactory getFactory(Principal principal){
        return factories.get(principal.getName());
    }
    
    public EntityManager getManager(Principal principal){
        return managers.get(principal.getName());
    }
    
    public EntityManagerFactory getDefaultFactory(){
        return defaultFactory;
    }
    
    public EntityManager getDefaultManager(){
        return defaultManager;
    }
}
