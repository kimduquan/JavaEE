/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.io.Serializable;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    
    @Inject
    private Application application;
    
    @Inject
    private Principal principal;
    
    private EntityManagerFactory factory;
    private EntityManager manager;
    
    @PostConstruct
    void postConstruct(){
        factory = application.getFactory(principal);
        manager = application.getManager(principal);
    }
    
    @Produces
    public EntityManagerFactory getFactory(){
        return factory;
    }
    
    public EntityManager getManager(){
        return manager;
    }
}
