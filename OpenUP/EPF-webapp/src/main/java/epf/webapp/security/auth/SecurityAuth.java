package epf.webapp.security.auth;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.naming.Naming;
import epf.security.auth.openid.AuthRequest;
import epf.security.auth.openid.Provider;
import epf.security.auth.openid.provider.StandardProvider;
import epf.util.logging.LogManager;
import epf.webapp.ConfigSource;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SecurityAuth {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(SecurityAuth.class.getName());
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigSource config;
	
	/**
	 * 
	 */
	private transient Provider googleProvider;
	
	/**
	 * 
	 */
	private transient Provider facebookProvider;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI googleDiscoveryUrl = new URI(config.getProperty(Naming.Security.Auth.GOOGLE_PROVIDER));
			googleProvider = new StandardProvider(googleDiscoveryUrl);
			final URI facebookDiscoveryUrl = new URI(config.getProperty(Naming.Security.Auth.FACEBOOK_PROVIDER));
			facebookProvider = new StandardProvider(facebookDiscoveryUrl);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityAuth.googleProvider]");
		}
	}
	
	/**
	 * @param authFlow
	 * @param authRequest
	 * @return
	 */
	public Provider initGoogleProvider(final AuthFlow authFlow, final AuthRequest authRequest) {
		authFlow.setClientSecret(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_SECRET).toCharArray());
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		return googleProvider;
	}
	
	/**
	 * @param authFlow
	 * @param authRequest
	 * @return
	 */
	public Provider initFacebookProvider(final AuthFlow authFlow, final AuthRequest authRequest) {
		authFlow.setClientSecret(config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_SECRET).toCharArray());
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		return facebookProvider;
	}
	
	/**
	 * @param issuer
	 * @return
	 */
	public Provider getProvider(final String issuer) {
		if(issuer.contains("google.com")) {
			return googleProvider;
		}
		else if(issuer.contains("facebook.com")) {
			return facebookProvider;
		}
		return null;
	}
}
