package epf.webapp.security.auth;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.naming.Naming;
import epf.security.auth.Provider;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.discovery.ProviderMetadata;
import epf.util.logging.LogManager;
import epf.webapp.ConfigSource;
import epf.webapp.security.auth.core.CodeFlow;
import epf.webapp.security.auth.core.ImplicitFlow;
import epf.security.auth.StandardProvider;

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
	private transient Provider googleProvider;
	
	/**
	 * 
	 */
	private transient URI googleDiscoveryUrl;
	
	/**
	 * 
	 */
	private transient Provider facebookProvider;
	
	/**
	 * 
	 */
	private transient URI facebookDiscoveryUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigSource config;
	
	/**
	 * 
	 */
	private transient final Map<String, Provider> providers = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			googleDiscoveryUrl = new URI(config.getProperty(Naming.Security.Auth.GOOGLE_PROVIDER));
			googleProvider = new StandardProvider(googleDiscoveryUrl, config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_SECRET).toCharArray());
			facebookDiscoveryUrl = new URI(config.getProperty(Naming.Security.Auth.FACEBOOK_PROVIDER));
			facebookProvider = new StandardProvider(facebookDiscoveryUrl, config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_SECRET).toCharArray());
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityAuth.providers]");
		}
	}
	
	/**
	 * @param authFlow
	 * @param authRequest
	 * @return
	 * @throws Exception
	 */
	public ProviderMetadata initGoogleProvider(final CodeFlow authFlow, final AuthRequest authRequest) throws Exception {
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		final ProviderMetadata metadata = authFlow.getProviderConfig(googleDiscoveryUrl);
		providers.put(metadata.getIssuer(), googleProvider);
		return metadata;
	}
	
	/**
	 * @param authFlow
	 * @param authRequest
	 * @return
	 * @throws Exception
	 */
	public ProviderMetadata initFacebookProvider(final ImplicitFlow authFlow, final AuthRequest authRequest) throws Exception {
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		final ProviderMetadata metadata = authFlow.getProviderConfig(facebookDiscoveryUrl);
		providers.put(metadata.getIssuer(), facebookProvider);
		return metadata;
	}
	
	/**
	 * @param issuer
	 * @return
	 */
	public Provider getProvider(final String issuer) {
		return providers.get(issuer);
	}
}
