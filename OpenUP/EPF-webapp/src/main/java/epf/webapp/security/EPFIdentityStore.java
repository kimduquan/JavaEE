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
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
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
public class EPFIdentityStore implements IdentityStore {
    
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
        	final TokenPrincipal principal = new TokenPrincipal(token);
            principals.put(token.getTokenID(), principal);
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
            	if(jwt != null) {
            		jwt.setRawToken(token);
            	}
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
    
    /**
     * @param name
     * @return
     */
    public TokenPrincipal getPrincipal(final String name) {
    	return principals.get(name);
    }
}
