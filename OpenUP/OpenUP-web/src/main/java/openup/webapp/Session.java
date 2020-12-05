/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import openup.webapp.security.TokenPrincipal;
import openup.client.security.Token;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
@Named("webapp_session")
public class Session implements Serializable {
    
    private Token token;
    
    @Inject
    private SecurityContext context;
    
    public Session(){
        
    }
    
    public Token getToken(){
        if(token == null){
            if(context.getCallerPrincipal() instanceof TokenPrincipal){
                token = ((TokenPrincipal)context.getCallerPrincipal()).getToken();
            }
        }
        return token;
    }
}
