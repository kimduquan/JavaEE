/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.client.registry.LocateRegistry;
import epf.util.client.Client;
import epf.util.client.ClientQueue;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class ConfigSource implements org.eclipse.microprofile.config.spi.ConfigSource {
    
    /**
     * 
     */
    private final transient Map<String, String> configs;
    
    /**
     * 
     */
    @Inject
    private transient LocateRegistry registry;
    
    /**
     * 
     */
    @Inject
    private transient ClientQueue clients;
    
    /**
     * 
     */
    @Inject
    private transient Logger logger;
    
    /**
     * 
     */
    public ConfigSource() {
    	super();
        configs = new ConcurrentHashMap<>();
    }
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct(){
        try(Client client = new Client(clients, registry.lookup("config"), b -> b)) {
            final Map<String, String> data = Config.getProperties(client, null);
            if(data != null){
                configs.putAll(data);
            }
        } 
        catch(Exception ex) {
        	logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
    }

	@Override
	public Map<String, String> getProperties() {
		return configs;
	}

	@Override
	public String getValue(final String propertyName) {
		return configs.getOrDefault(propertyName, null);
	}

	@Override
	public String getName() {
		return "EPF";
	}
}
