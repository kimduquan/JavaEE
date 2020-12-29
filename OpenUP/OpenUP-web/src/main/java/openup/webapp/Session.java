/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import openup.webapp.security.TokenPrincipal;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
@Named("webapp_session")
public class Session implements Serializable {
    
    @Inject
    private SecurityContext context;
    
    @Inject
    private openup.client.Session client;
    
    @PostConstruct
    void postConstruct(){
        if(context.getCallerPrincipal() instanceof TokenPrincipal){
            TokenPrincipal principal = ((TokenPrincipal)context.getCallerPrincipal());
            client.header().setToken(principal.getToken().getRawToken());
        }
    }
    
    public openup.client.Session getClient(){
        return client;
    }
}
