/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp;

import java.io.Serializable;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.client.security.Security;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.logging.Logging;
import epf.webapp.security.TokenPrincipal;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
@Named("webapp_session")
public class Session implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TokenPrincipal principal;
    private Logger logger;
    
    @Inject
    private SecurityContext context;
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private ClientQueue clients;
    
    @PostConstruct
    void postConstruct(){
        if(context.getCallerPrincipal() instanceof TokenPrincipal){
            principal = ((TokenPrincipal)context.getCallerPrincipal());
        }
        logger = Logging.getLogger(Session.class.getName());
    }
    
    @PreDestroy
    void preDestroy(){
        if(principal != null){
        	String securityUrl = config.getValue(ConfigNames.SECURITY_URL);
        	try(Client client = newClient(new URI(securityUrl))) {
            	Security.logOut(client, null);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "@PreDestroy", ex);
            }
        }
    }
    
    public Client newClient(URI uri) {
    	if(context.getCallerPrincipal() instanceof TokenPrincipal){
            principal = ((TokenPrincipal)context.getCallerPrincipal());
        }
    	Client client = new Client(clients, uri, b -> b);
    	if(principal != null) {
        	client.authorization(principal.getToken().getRawToken());
    	}
    	return client;
    }
}
