package epf.webapp.security;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
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
import epf.webapp.GatewayUtil;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.OpenIDPrincipal;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.auth.openid.TokenResponse;
import epf.security.client.Security;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class EPFIdentityStore implements IdentityStore {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EPFIdentityStore.class.getName());
    
    /**
     * 
     */
    @Inject
    private transient GatewayUtil gatewayUtil;
    
    /**
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final UsernamePasswordCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        try {
        	final URI webAppUrl = ConfigUtil.getURI(Naming.WebApp.WEB_APP_URL);
        	try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
        		final String rawToken = Security.login(
            			client,
    					credential.getCaller(),
    					credential.getPasswordAsString(),
    					webAppUrl.toURL()
    					);
                if(rawToken != null){
                	final Set<String> groups = new HashSet<>();
                	groups.add(Naming.Security.DEFAULT_ROLE);
                	result = new CredentialValidationResult(new TokenPrincipal(credential.getCaller(), rawToken), groups);
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
    	final Entry<String, String> clientSecret = new AbstractMap.SimpleEntry<>("client_secret", new String(credential.getClientSecret()));
    	try {
        	final TokenResponse tokenResponse = credential.getProvider().accessToken(credential.getTokenRequest(), Arrays.asList(clientSecret));
        	if(tokenResponse != null) {
        		final OpenIDPrincipal principal = new OpenIDPrincipal(credential.getTokenRequest().getCode(), tokenResponse, credential.getProvider().discovery());
        		final Set<String> groups = new HashSet<>(Arrays.asList(Naming.Security.DEFAULT_ROLE));
        		result = new CredentialValidationResult(principal, groups);
        	}
    	}
    	catch(Exception e) {
    		LOGGER.log(Level.SEVERE, "validate", e);
    	}
    	return result;
    }
}
