package epf.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Path(Naming.CONFIG)
public class Config implements epf.client.config.Config {
    
    /**
     * 
     */
    private final transient Map<String, String> configs = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT)
	@Inject
	private transient int firstResultDefault;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT)
	@Inject
	private transient int maxResultsDefault;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_PROVIDER)
	@Inject
	private transient String googleOpenIDDiscoveryUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_CLIENT_ID)
	@Inject
	private transient String googleClientId;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.GOOGLE_CLIENT_SECRET)
	@Inject
	private transient String googleClientSecret;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.AUTH_URL)
    @Inject
    private transient String authUrl;
    
    /**
     * 
     */
    @ConfigProperty(name = Naming.Security.Auth.FACEBOOK_PROVIDER)
	@Inject
	private transient String facebookOpenIDDiscoveryUrl;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	configs.put(Naming.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT, String.valueOf(firstResultDefault));
    	configs.put(Naming.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT, String.valueOf(maxResultsDefault));
    	configs.put(Naming.Security.Auth.GOOGLE_PROVIDER, googleOpenIDDiscoveryUrl);
    	configs.put(Naming.Security.Auth.GOOGLE_CLIENT_ID, googleClientId);
    	configs.put(Naming.Security.Auth.GOOGLE_CLIENT_SECRET, googleClientSecret);
    	configs.put(Naming.Security.Auth.FACEBOOK_PROVIDER, facebookOpenIDDiscoveryUrl);
    	configs.put(Naming.Security.Auth.AUTH_URL, authUrl);
    }
    
    @Override
    @PermitAll
    public Map<String, String> getProperties(final String name) {
        return configs;
    }
}
