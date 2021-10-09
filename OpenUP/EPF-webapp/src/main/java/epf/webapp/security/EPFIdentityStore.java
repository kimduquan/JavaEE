/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.security;

import java.net.URI;
import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import epf.util.config.ConfigUtil;
import epf.util.logging.Logging;
import epf.util.security.PasswordUtil;
import epf.webapp.WebApp;
import epf.client.gateway.GatewayUtil;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.client.util.Client;
import epf.client.util.ClientQueue;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class EPFIdentityStore implements IdentityStore {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(EPFIdentityStore.class.getName());
    
    /**
     * 
     */
    @Inject
    private transient ClientQueue clients;
    
    /**
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final BasicAuthenticationCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        try {
        	final String passwordHash = PasswordUtil.hash(credential.getCaller(), credential.getPassword().getValue());
        	final URL webAppUrl = new URL(ConfigUtil.getString(WebApp.WEBAPP_URL));
        	final URI securityUrl = GatewayUtil.get("security");
        	Token token = null;
        	try(Client client = new Client(clients, securityUrl, b -> b)){
        		final String rawToken = Security.login(
            			client,
    					credential.getCaller(),
    					passwordHash,
    					webAppUrl
    					);
                if(!rawToken.isEmpty()){
                	client.authorization(rawToken);
                	token = Security.authenticate(client);
                	if(token != null) {
                		token.setRawToken(rawToken);
                	}
                }
            }
            if(token != null){
            	result = new CredentialValidationResult(token.getName(), token.getGroups());
            }
        }
        catch(Exception ex) {
        	LOGGER.throwing(getClass().getName(), "validate", ex);
        }
        return result;
    }
    
    @Override
    public Set<String> getCallerGroups(final CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }
}
