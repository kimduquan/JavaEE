/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp;

import java.io.Serializable;
import java.net.URI;
import java.security.Principal;
import java.util.Set;
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

	/**
	 * 
	 */
	private transient TokenPrincipal principal;
    /**
     * 
     */
	@Inject
    private transient Logger logger;
    
    /**
     * 
     */
    @Inject
    private transient SecurityContext context;
    
    /**
     * 
     */
    @Inject
    private transient LocateRegistry registry;
    
    /**
     * 
     */
    @Inject
    private transient ClientQueue clients;
    
    /**
     * 
     */
    @Inject
    private transient EPFIdentityStore identityStore;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct(){
    	Principal current = context.getCallerPrincipal();
    	if(current != null) {
    		Set<TokenPrincipal> token = context.getPrincipalsByType(TokenPrincipal.class);
    		if(token.isEmpty()) {
    			principal = identityStore.getPrincipal(current.getName());
    		}
    	}
    }
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
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
    
    /**
     * @param uri
     * @return
     */
    public Client newClient(final URI uri) {
    	final Client client = new Client(clients, uri, b -> b);
    	if(principal != null) {
        	client.authorization(principal.getToken().getRawToken());
    	}
    	return client;
    }
    
    /**
     * @param name
     * @return
     */
    public Client newClient(final String name) {
    	final Client client = new Client(clients, registry.lookup(name), b -> b);
    	if(principal != null) {
        	client.authorization(principal.getToken().getRawToken());
    	}
    	return client;
    }
}
