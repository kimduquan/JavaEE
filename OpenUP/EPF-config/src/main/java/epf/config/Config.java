package epf.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.util.io.FileUtil;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Path(Naming.CONFIG)
public class Config implements epf.config.client.Config {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Config.class.getName());
    
    /**
     * 
     */
    private final transient Map<String, String> configs = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.WebApp.Messaging.MESSAGES_LIMIT)
	@Inject
	transient long messagesLimit;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Query.FIRST_RESULT_DEFAULT)
	@Inject
	transient int firstResultDefault;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Query.MAX_RESULTS_DEFAULT)
	@Inject
	transient int maxResultsDefault;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_PROVIDER)
	@Inject
	transient String googleOpenIDDiscoveryUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_CLIENT_ID)
	@Inject
	transient String googleClientId;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_CLIENT_SECRET)
	@Inject
	transient String googleClientSecret;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.AUTH_URL)
    @Inject
    transient String authUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_PROVIDER)
	@Inject
	transient String facebookOpenIDDiscoveryUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_CLIENT_ID)
	@Inject
	transient String facebookClientId;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_CLIENT_SECRET)
	@Inject
	transient String facebookClientSecret;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.JWT.VERIFIER_PUBLIC_KEY_LOCATION)
    @Inject
    transient String publicKeyLocation;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	configs.put(Naming.Query.FIRST_RESULT_DEFAULT, String.valueOf(firstResultDefault));
    	configs.put(Naming.Query.MAX_RESULTS_DEFAULT, String.valueOf(maxResultsDefault));
    	configs.put(Naming.Security.Auth.GOOGLE_PROVIDER, googleOpenIDDiscoveryUrl);
    	configs.put(Naming.Security.Auth.GOOGLE_CLIENT_ID, googleClientId);
    	configs.put(Naming.Security.Auth.GOOGLE_CLIENT_SECRET, googleClientSecret);
    	configs.put(Naming.Security.Auth.FACEBOOK_PROVIDER, facebookOpenIDDiscoveryUrl);
    	configs.put(Naming.Security.Auth.FACEBOOK_CLIENT_ID, facebookClientId);
    	configs.put(Naming.Security.Auth.FACEBOOK_CLIENT_SECRET, facebookClientSecret);
    	configs.put(Naming.Security.Auth.AUTH_URL, authUrl);
    	configs.put(Naming.WebApp.Messaging.MESSAGES_LIMIT, String.valueOf(messagesLimit));
    	try {
        	configs.put(Naming.Security.JWT.VERIFIER_PUBLIC_KEY, FileUtil.readString(publicKeyLocation, "UTF-8"));
    	}
    	catch(Exception ex) {
    		LOGGER.log(Level.SEVERE, Naming.Security.JWT.VERIFIER_PUBLIC_KEY, ex);
    	}
    }
    
    @Override
    @PermitAll
    public Map<String, String> getProperties(final String name) {
        return configs;
    }
}
