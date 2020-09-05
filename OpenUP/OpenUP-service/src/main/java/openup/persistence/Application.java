/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
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
    
    @PersistenceUnit
    private EntityManagerFactory defaultFactory;
    
    @PersistenceContext
    private EntityManager defaultManager;
    
    private Map<String, EntityManagerFactory> factories;
    
    @PostConstruct
    void postConstruct(){
        factories = new ConcurrentHashMap();
    }
    
    @PreDestroy
    void preDestroy(){
        factories.values().forEach((factory) -> {
            factory.close();
        });
        factories.clear();
    }
    
    public boolean createEntityManagerFactory(String userName, String password){
        if(factories.containsKey(userName) == false){
            Map<String, String> props = new HashMap<>();
            props.put("javax.persistence.jdbc.user", userName);
            props.put("javax.persistence.jdbc.password", password);
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("OpenUP-service", props);
            factories.put(userName, factory);
            return true;
        }
        return false;
    }
    
    public EntityManagerFactory getEntityManagerFactory(String userName){
        return factories.getOrDefault(userName, defaultFactory);
    }
}
