/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.security;

import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import epf.client.registry.LocateRegistry;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.client.webapp.WebApp;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.logging.Logging;
import epf.util.security.PasswordHelper;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = EPF.SCHEMA)
public class EPFIdentityStore implements IdentityStore {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(EPFIdentityStore.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, String> credentials = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Token> tokens = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    /**
     * 
     */
    private transient final Map<String, TokenPrincipal> principals = new ConcurrentHashMap<>();
	
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
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final BasicAuthenticationCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        try {
            final String oldHashPassword = credentials.get(credential.getCaller());
            final String passwordHash = PasswordHelper.hash(credential.getCaller(), credential.getPassword().getValue());
            
            Token token = null;
            if(passwordHash.equals(oldHashPassword)) {
            	token = tokens.get(credential.getCaller());
            }
            else {
                final URL webAppUrl = new URL(System.getenv(WebApp.WEBAPP_URL));
            	final URI securityUrl = registry.lookup("security");
            	try(Client client = new Client(clients, securityUrl, b -> b)){
            		final String rawToken = Security.login(
                			client, 
                			null,
        					credential.getCaller(),
        					passwordHash,
        					webAppUrl
        					);
                    if(!rawToken.isEmpty()){
                    	client.authorization(rawToken);
                    	token = Security.authenticate(client, null);
                    	if(token != null) {
                    		token.setRawToken(rawToken);
                    		tokens.put(credential.getCaller(), token);
                            credentials.put(credential.getCaller(), passwordHash);
                    	}
                    }
                }
            }
            
            if(token != null){
            	final Token tempToken = token;
            	final TokenPrincipal principal = principals.computeIfAbsent(token.getTokenID(), id -> new TokenPrincipal(credential.getCaller(), tempToken));
                result = new CredentialValidationResult(principal, principal.getToken().getGroups());
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
    
    /**
     * @param name
     * @return
     */
    public TokenPrincipal peekPrincipal(final String name) {
    	final TokenPrincipal principal = principals.remove(name);
    	if(principal != null) {
    		credentials.remove(principal.getCaller());
    		tokens.remove(principal.getCaller());
    	}
    	return principal;
    }
}
