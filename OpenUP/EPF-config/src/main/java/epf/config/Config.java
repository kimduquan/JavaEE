/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Path("config")
public class Config implements epf.client.config.Config {
    
    /**
     * 
     */
    private final transient Map<String, String> configs = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @ConfigProperty(name = epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT)
	@Inject
	private transient int firstResultDefault;
    
    /**
     * 
     */
    @ConfigProperty(name = epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT)
	@Inject
	private transient int maxResultsDefault;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	configs.put(epf.client.persistence.Persistence.PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT, String.valueOf(firstResultDefault));
    	configs.put(epf.client.persistence.Persistence.PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT, String.valueOf(maxResultsDefault));
    }
    
    @Override
    @PermitAll
    public Map<String, String> getProperties(final String name) {
        return configs;
    }
}
