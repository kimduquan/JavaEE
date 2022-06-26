package epf.webapp.security;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.TokenPrincipal;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.ImplicitCredential;
import epf.webapp.security.auth.SecurityAuth;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.auth.Provider;
import epf.security.auth.core.TokenResponse;
import epf.security.client.Security;
import epf.security.schema.Token;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class AuthIdentityStore implements IdentityStore {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(AuthIdentityStore.class.getName());
    
    /**
     * 
     */
    @Inject
    private transient GatewayUtil gatewayUtil;
    
    /**
     * 
     */
    @Inject
    private transient SecurityAuth securityAuth;
    
    /**
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final UsernamePasswordCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        try {
        	final URL webAppUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
        	try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
        		final String rawToken = Security.login(
            			client,
    					credential.getCaller(),
    					credential.getPasswordAsString(),
    					webAppUrl
    					);
                if(rawToken != null){
                	final Set<String> groups = new HashSet<>();
                	groups.add(Naming.Security.DEFAULT_ROLE);
                	result = new CredentialValidationResult(new TokenPrincipal(credential.getCaller(), rawToken.toCharArray()), groups);
                }
            }
        }
        catch(Exception ex) {
        	LOGGER.log(Level.WARNING, "validate", ex);
        }
        return result;
    }
    
    @Override
    public Set<String> getCallerGroups(final CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }
    
    /**
     * @param credential
     * @return
     */
    public CredentialValidationResult validate(final AuthCodeCredential credential) {
    	CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
    	final Provider provider = securityAuth.getProvider(credential.getProvider());
    	try {
        	final TokenResponse tokenResponse = provider.accessToken(credential.getTokenRequest());
        	if(tokenResponse != null) {
        		final URL webAppUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
        		try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
        			final Token token = Security.authenticateIDToken(client, credential.getProvider(), credential.getSessionId(), tokenResponse.getId_token(), webAppUrl);
        			final TokenPrincipal principal = new TokenPrincipal(token.getName(), token.getRawToken().toCharArray());
        			result = new CredentialValidationResult(principal, token.getGroups());
        		}
        	}
    	}
    	catch(Exception e) {
    		LOGGER.log(Level.SEVERE, "validate", e);
    	}
    	return result;
    }
    
    /**
     * @param credential
     * @return
     * @throws Exception 
     */
    public CredentialValidationResult validate(final ImplicitCredential credential) throws Exception {
    	CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
    	final URL webAppUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
    	try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
    		final Token token = Security.authenticateIDToken(client, credential.getProvider(), credential.getSessionId(), credential.getAuthResponse().getId_token(), webAppUrl);
    		final TokenPrincipal principal = new TokenPrincipal(token.getName(), token.getRawToken().toCharArray());
			result = new CredentialValidationResult(principal, token.getGroups());
    	}
    	return result;
    }
}
