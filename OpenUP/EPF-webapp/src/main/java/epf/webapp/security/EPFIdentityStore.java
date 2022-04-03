package epf.webapp.security;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import org.jose4j.jwt.JwtClaims;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.IDTokenPrincipal;
import epf.webapp.security.auth.ImplicitCredential;
import epf.webapp.security.auth.SecurityAuth;
import epf.webapp.security.util.JwtUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.auth.Provider;
import epf.security.auth.core.TokenResponse;
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
    	final Provider provider = securityAuth.getProvider(credential.getProviderMetadata().getIssuer());
    	try {
        	final TokenResponse tokenResponse = provider.accessToken(credential.getTokenRequest());
        	if(tokenResponse != null) {
        		final JwtClaims claims = JwtUtil.decode(tokenResponse.getId_token());
        		final IDTokenPrincipal principal = new IDTokenPrincipal(claims.getSubject(), tokenResponse.getId_token(), claims.getClaimsMap());
        		final Set<String> groups = new HashSet<>(Arrays.asList(Naming.Security.DEFAULT_ROLE));
        		result = new CredentialValidationResult(principal, groups);
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
    	final JwtClaims claims = JwtUtil.decode(credential.getAuthResponse().getId_token());
    	final Provider provider = securityAuth.getProvider(claims.getIssuer());
    	if(provider.validateIDToken(credential.getAuthResponse().getId_token(), credential.getSessionId())) {
        	final IDTokenPrincipal principal = new IDTokenPrincipal(claims.getSubject(), credential.getAuthResponse().getId_token(), claims.getClaimsMap());
    		final Set<String> groups = new HashSet<>();
    		groups.add(Naming.Security.DEFAULT_ROLE);
    		return new CredentialValidationResult(principal, groups);
    	}
    	return CredentialValidationResult.INVALID_RESULT;
    }
}
