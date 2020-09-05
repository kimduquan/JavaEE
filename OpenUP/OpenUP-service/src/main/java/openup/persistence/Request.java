/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Request {
    
    @Inject
    private EntityManagerFactory factory;
    
    private EntityManager manager;
    
    @PostConstruct
    void postConstruct(){
        manager = factory.createEntityManager();
    }
    
    @PreDestroy
    void preDestroy(){
        manager.close();
    }
    
    @Produces
    public EntityManager getEntityManager(){
        return manager;
    }
}
