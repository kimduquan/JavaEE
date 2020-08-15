/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Context;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class OpenUPPersistence {
    
    @PersistenceUnit
    private EntityManager entityManager;
    
    private Map<String, EntityManager> managers;
    
    @PreDestroy
    void preDestroy(){
        managers.clear();
    }
    
    @PostConstruct
    void postConstruct(){
        managers = new HashMap<>();
    }
    
    @Produces @SessionScoped
    EntityManager getEntityManager(@Context Principal principal){
        return managers.get(principal.getName());
    }
    
    public boolean createEntityManager(String userName, String password){
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password);
        EntityManager manager = entityManager.getEntityManagerFactory().createEntityManager(props);
        if(manager.isOpen()){
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            if(transaction.isActive()){
                transaction.commit();
                manager = managers.putIfAbsent(userName, manager);
                if(manager != null){
                    if(manager.isOpen()){
                        manager.close();
                    }
                }
                return true;
            }
        }
        return false;
    }
}
