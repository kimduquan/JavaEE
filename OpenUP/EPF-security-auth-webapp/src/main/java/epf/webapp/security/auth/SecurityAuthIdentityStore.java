package epf.webapp.security.auth;

import java.net.URL;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import epf.util.config.ConfigUtil;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.TokenPrincipal;
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
public class SecurityAuthIdentityStore implements IdentityStore {
    
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
    
    @Override
    public Set<String> getCallerGroups(final CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }
    
    /**
     * @param credential
     * @return
     */
    public CredentialValidationResult validate(final AuthCodeCredential credential) throws Exception {
    	CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
    	final Provider provider = securityAuth.getProvider(credential.getProvider());
    	final TokenResponse tokenResponse = provider.accessToken(credential.getTokenRequest());
    	if(tokenResponse != null) {
    		final URL webAppUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
    		try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
    			final Token token = Security.authenticateIDToken(client, credential.getProvider(), credential.getSessionId(), tokenResponse.getId_token(), webAppUrl);
    			final TokenPrincipal principal = new TokenPrincipal(token.getName(), token.getRawToken().toCharArray());
    			result = new CredentialValidationResult(principal, token.getGroups());
    		}
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
