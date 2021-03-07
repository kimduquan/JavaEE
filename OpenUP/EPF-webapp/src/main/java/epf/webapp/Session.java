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
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import epf.client.registry.LocateRegistry;
import epf.client.security.Security;
import epf.schema.roles.Role;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.logging.Logging;
import epf.webapp.security.EPFIdentityStore;
import epf.webapp.security.TokenPrincipal;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
@Named("webapp_session")
@RolesAllowed(Role.DEFAULT_ROLE)
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
    private LocateRegistry registry;
    
    @Inject
    private ClientQueue clients;
    
    @Inject
    private EPFIdentityStore identityStore;
    
    @PostConstruct
    void postConstruct(){
    	if(context.getCallerPrincipal() != null) {
    		principal = identityStore.getPrincipal(context.getCallerPrincipal().getName());
    	}
    	logger = Logging.getLogger(Session.class.getName());
    }
    
    @PreDestroy
    void preDestroy(){
        if(principal != null){
        	try(Client client = newClient(registry.lookup("security"))) {
        		client.authorization(principal.getToken().getRawToken());
            	Security.logOut(client, null);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "PreDestroy", ex);
            }
        }
    }
    
    public Client newClient(URI uri) {
    	Client client = new Client(clients, uri, b -> b);
    	if(principal != null) {
        	client.authorization(principal.getToken().getRawToken());
    	}
    	return client;
    }
    
    public Client newClient(String name) {
    	Client client = new Client(clients, registry.lookup(name), b -> b);
    	if(principal != null) {
        	client.authorization(principal.getToken().getRawToken());
    	}
    	return client;
    }
}
