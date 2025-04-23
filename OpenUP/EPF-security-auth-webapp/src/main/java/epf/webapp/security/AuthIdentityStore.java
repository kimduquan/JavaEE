package epf.webapp.security;

import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
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

@ApplicationScoped
public class AuthIdentityStore implements IdentityStore {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthIdentityStore.class.getName());
    
    @Inject
    private transient GatewayUtil gatewayUtil;
    
    @Inject
    private transient SecurityAuth securityAuth;
    
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
                if(rawToken != null && !rawToken.isEmpty()){
                	client.authorization(rawToken.toCharArray());
                	final Token token = Security.authenticate(client);
                	result = new CredentialValidationResult(new TokenPrincipal(token.getName(), rawToken.toCharArray(), token.getClaims()), token.getGroups());
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
    
    public CredentialValidationResult validate(final AuthCodeCredential credential) {
    	CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
    	final Provider provider = securityAuth.getProvider(credential.getProvider());
    	try {
        	final TokenResponse tokenResponse = provider.accessToken(credential.getTokenRequest());
        	if(tokenResponse != null) {
        		final URL webAppUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
        		try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
        			final Token token = Security.authenticateIDToken(client, credential.getProvider(), credential.getSessionId(), tokenResponse.getId_token(), webAppUrl);
        			final TokenPrincipal principal = new TokenPrincipal(token.getName(), token.getRawToken().toCharArray(), token.getClaims());
        			result = new CredentialValidationResult(principal, token.getGroups());
        		}
        	}
    	}
    	catch(Exception e) {
    		LOGGER.log(Level.SEVERE, "validate", e);
    	}
    	return result;
    }
    
    public CredentialValidationResult validate(final ImplicitCredential credential) throws Exception {
    	CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
    	final URL webAppUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
    	try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
    		final Token token = Security.authenticateIDToken(client, credential.getProvider(), credential.getSessionId(), credential.getAuthResponse().getId_token(), webAppUrl);
    		final TokenPrincipal principal = new TokenPrincipal(token.getName(), token.getRawToken().toCharArray(), token.getClaims());
			result = new CredentialValidationResult(principal, token.getGroups());
    	}
    	return result;
    }
}
