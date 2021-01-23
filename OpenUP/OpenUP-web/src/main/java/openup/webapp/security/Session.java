/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import epf.schema.OpenUP;
import java.io.Serializable;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.client.security.Header;
import openup.client.security.Security;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    
    private TokenPrincipal principal;
    
    @Inject
    private SecurityContext context;
    
    @Inject
    private ConfigSource config;
    
    @PostConstruct
    void postConstruct(){
        principal = (TokenPrincipal) context.getCallerPrincipal();
    }
    
    @PreDestroy
    void preDestroy(){
        if(principal != null){
            try {
                String base = config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "");
                URL baseUrl = new URL(base);
                Header header = new Header();
                Security service = RestClientBuilder
                        .newBuilder()
                        .baseUrl(baseUrl)
                        .register(header)
                        .build(Security.class);
                header.setToken(principal.getToken().getRawToken());
                service.logOut(OpenUP.Schema);
            } 
            catch (Exception ex) {
                Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
