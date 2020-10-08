/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Request {
    
    @Inject
    private Session session;
    
    @PersistenceContext(name = "EPF", unitName = "EPF")
    private EntityManager defaultManager;
    
    @Context
    private SecurityContext context;
    
    private EntityManager manager;
    
    @PostConstruct
    void postConstruct(){
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            manager = session.getFactory(principal).createEntityManager();
        }
    }
    
    @PreDestroy
    void preDestroy(){
        if(manager != null){
            manager.close();
        }
    }
    
    @Produces
    public EntityManager getManager(){
        if(manager != null){
            return manager;
        }
        return defaultManager;
    }
}
