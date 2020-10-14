/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Request {
    
    @Inject
    private Application application;
    
    @PersistenceContext(name = "EPF", unitName = "EPF")
    private EntityManager defaultManager;
    
    private EntityManager manager;
    
    @PreDestroy
    void preDestroy(){
        if(manager != null){
            manager.close();
        }
    }
    
    public EntityManager getManager(SecurityContext context){
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            Session session = application.getSession(principal.getName());
            if(principal instanceof JsonWebToken){
                JsonWebToken jwt = (JsonWebToken)principal;
                return session.getManager(jwt.getTokenID());
            }
            else{
                manager = session.getFactory().createEntityManager();
                return manager;
            }
        }
        return defaultManager;
    }
}
