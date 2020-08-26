/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Context;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Timeout;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class OpenUPPersistence {
    
    @PersistenceUnit
    private EntityManagerFactory factory;
    
    @PersistenceContext
    private EntityManager defaultEntityManager;
    
    private Map<String, EntityManager> managers;
    
    @PreDestroy
    void preDestroy(){
        managers.forEach((name, entityManager) -> {
            entityManager.close();
        });
        managers.clear();
    }
    
    @PostConstruct
    void postConstruct(){
        managers = new ConcurrentHashMap<>();
    }
    
    @Produces @Dependent
    EntityManager getEntityManager(@Context Principal principal){
        return managers.getOrDefault(principal.getName(), defaultEntityManager);
    }
    
    @Asynchronous
    @Timeout(4000)
    public CompletionStage<EntityManager> createEntityManager(String userName, String password){
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password);
        CompletionStage<EntityManager> res = CompletableFuture.supplyAsync(() -> {
            EntityManager manager = factory.createEntityManager(props);
            EntityManager oldManager = managers.putIfAbsent(userName, manager);
            if(oldManager != null){
                oldManager.close();
            }
            return manager;
        });
        return res;
    }
}
