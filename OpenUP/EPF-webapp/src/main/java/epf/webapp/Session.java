/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp;

import java.io.Serializable;
import java.security.Principal;
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
	private TokenPrincipal principal;
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
    	final Principal current = context.getCallerPrincipal();
    	if(current != null) {
    		principal = identityStore.peekPrincipal(current.getName());
    	}
    }
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
        if(principal != null){
        	try(Client client = newClient("security")) {
        		client.authorization(principal.getToken().getRawToken());
            	Security.logOut(client, null);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "PreDestroy", ex);
            }
        }
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
