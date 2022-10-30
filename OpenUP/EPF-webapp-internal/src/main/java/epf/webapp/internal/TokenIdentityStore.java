package epf.webapp.internal;

import java.net.URL;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import epf.naming.Naming;
import epf.security.auth.util.JwtUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class TokenIdentityStore {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(TokenIdentityStore.class.getName());
    
	/**
	 * 
	 */
	private transient final JwtUtil jwtUtil = new JwtUtil();
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil config;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final String verifyKeyText = config.getProperty(Naming.Security.JWT.VERIFIER_PUBLIC_KEY);
			final URL webAppUrl = epf.util.config.ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
			final PublicKey verifyKey = KeyUtil.decodePublicKey("RSA", verifyKeyText.getBytes("UTF-8"));
			jwtUtil.initialize(Naming.EPF, webAppUrl, verifyKey);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[TokenIdentityStore.jwtUtil]", ex);
		}
	}
    
    /**
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final TokenCredential credential) throws Exception {
    	final JwtClaims claims = jwtUtil.process(credential.getToken());
    	final Set<String> groups = new HashSet<>(claims.getStringListClaimValue(Claims.groups.name()));
    	final TokenPrincipal principal = new TokenPrincipal(claims.getSubject(), credential.getToken(), claims.getClaimsMap());
    	return new CredentialValidationResult(principal, groups);
    }
    
	/**
	* @param validationResult
	* @return
	*/
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }
}
