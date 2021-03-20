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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;

import epf.client.EPFException;
import epf.client.registry.LocateRegistry;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.security.PasswordHelper;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class EPFIdentityStore implements IdentityStore, RememberMeIdentityStore {
    
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
     * 
     */
    @Inject
    private transient Logger logger;
    
    /**
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final BasicAuthenticationCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        final Token token = login(credential);
        if(token != null){
        	final TokenPrincipal principal = new TokenPrincipal(credential.getCaller(), token);
            principals.put(credential.getCaller(), principal);
            result = new CredentialValidationResult(principal, principal.getToken().getGroups());
        }
        return result;
    }
    
    /**
     * @param credential
     * @return
     */
    protected Token login(final BasicAuthenticationCredential credential){
        final URI securityUrl = registry.lookup("security");
        URL audienceUrl;
        String passwordHash;
		try {
			audienceUrl = new URL(String.format(
			                            Security.AUDIENCE_URL_FORMAT,
			                            securityUrl.getScheme(), 
			                            securityUrl.getHost(), 
			                            securityUrl.getPort()
			                    ));
			passwordHash = PasswordHelper.hash(
					credential.getCaller(), 
					credential.getPassword().getValue()
					);
		} 
		catch (Exception e) {
			throw new EPFException(e);
		}
		Token jwt = null;
        try(Client client = new Client(clients, securityUrl, b -> b)){
        	final String token = Security.login(
        			client, 
        			null,
					credential.getCaller(),
					passwordHash,
					audienceUrl
					);
            if(!token.isEmpty()){
            	client.authorization(token);
            	jwt = Security.authenticate(client, null);
            }
        }
        catch (Exception e) {
			logger.warning(e.getMessage());
		}
        return jwt;
    }
    
    @Override
    public Set<String> getCallerGroups(final CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }

    @Override
    public CredentialValidationResult validate(final RememberMeCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        try(Client client = new Client(clients, registry.lookup("security"), b -> b)) {
        	client.authorization(credential.getToken());
        	final Token jwt = Security.authenticate(client, null);
            if(jwt != null){
            	final TokenPrincipal principal = new TokenPrincipal(jwt.getName(), jwt);
                result = new CredentialValidationResult(principal, jwt.getGroups());
            }
        } 
        catch (Exception ex) {
            logger.log(Level.SEVERE, "validate", ex);
        }
        return result;
    }

    @Override
    public String generateLoginToken(final CallerPrincipal caller, final Set<String> groups) {
    	String token = "";
        if(caller instanceof TokenPrincipal){
        	final TokenPrincipal principal = (TokenPrincipal) caller;
            token = principal.getToken().getRawToken();
        }
        return token;
    }

    @Override
    public void removeLoginToken(final String token) {
    	try(Client client = new Client(clients, registry.lookup("security"), b -> b)) {
            client.authorization(token);
            Security.logOut(client, null);
        } 
        catch (Exception ex) {
        	logger.log(Level.SEVERE, "removeLoginToken", ex);
        }
    }
    
    /**
     * @param name
     * @return
     */
    public TokenPrincipal getPrincipal(final String name) {
    	return principals.get(name);
    }
}
