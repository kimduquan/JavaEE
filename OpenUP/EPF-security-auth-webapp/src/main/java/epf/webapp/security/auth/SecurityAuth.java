package epf.webapp.security.auth;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import epf.naming.Naming;
import epf.security.auth.Provider;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.discovery.ProviderMetadata;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import epf.util.security.SecurityUtil;
import epf.webapp.internal.ConfigUtil;
import epf.webapp.security.auth.core.CodeFlow;
import epf.webapp.security.auth.core.ImplicitFlow;
import epf.security.auth.StandardProvider;

@ApplicationScoped
public class SecurityAuth {
	
	private transient final static Logger LOGGER = LogManager.getLogger(SecurityAuth.class.getName());
	
	private transient Provider googleProvider;
	
	private transient URI googleDiscoveryUrl;
	
	private transient Provider facebookProvider;
	
	private transient URI facebookDiscoveryUrl;
	
	@Inject
	private transient ConfigUtil config;
	
	private transient final Map<String, Provider> providers = new ConcurrentHashMap<>();
	
	private transient ClientBuilder clientBuilder;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			clientBuilder = ClientBuilder.newBuilder();
			googleDiscoveryUrl = new URI(config.getProperty(Naming.Security.Auth.GOOGLE_PROVIDER));
			final String clientSecretKey = config.getProperty(Naming.Security.Auth.CLIENT_SECRET_KEY);
			final SecretKey secretKey = KeyUtil.decodeFromString(clientSecretKey, "AES");
			final String googleSecret = config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_SECRET);
			final String googleClientSecret = SecurityUtil.decrypt(googleSecret, secretKey);
			googleProvider = new StandardProvider(googleDiscoveryUrl, googleClientSecret.toCharArray(), config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_ID), clientBuilder);
			facebookDiscoveryUrl = new URI(config.getProperty(Naming.Security.Auth.FACEBOOK_PROVIDER));
			final String facebookSecret = config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_SECRET);
			final String facebookClientSecret = SecurityUtil.decrypt(facebookSecret, secretKey);
			facebookProvider = new StandardProvider(facebookDiscoveryUrl, facebookClientSecret.toCharArray(), config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_ID), clientBuilder);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityAuth.providers]");
		}
	}
	
	public ProviderMetadata initGoogleProvider(final CodeFlow authFlow, final AuthRequest authRequest) throws Exception {
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		final ProviderMetadata metadata = authFlow.getProviderConfig(clientBuilder, googleDiscoveryUrl);
		providers.put(Naming.Security.Auth.GOOGLE, googleProvider);
		return metadata;
	}
	
	public ProviderMetadata initFacebookProvider(final ImplicitFlow authFlow, final AuthRequest authRequest) throws Exception {
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.FACEBOOK_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		final ProviderMetadata metadata = authFlow.getProviderConfig(clientBuilder, facebookDiscoveryUrl);
		providers.put(Naming.Security.Auth.FACEBOOK, facebookProvider);
		return metadata;
	}
	
	public Provider getProvider(final String provider) {
		return providers.get(provider);
	}
}
