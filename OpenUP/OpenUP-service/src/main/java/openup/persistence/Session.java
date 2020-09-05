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
    
    @PostConstruct
    void postConstruct(){
        factory = application.getEntityManagerFactory(principal.getName());
    }
    
    @Produces
    public EntityManagerFactory getEntityManagerFactory(){
        return factory;
    }
}
