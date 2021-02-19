/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.config;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.util.client.Client;
import epf.util.client.ClientQueue;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class ConfigSource {
	
	private static final Logger logger = Logger.getLogger(ConfigSource.class.getName());
    
    private Map<String, Object> configs;
    
    @Inject
    private ClientQueue clients;
    
    @PostConstruct
    void postConstruct(){
        configs = new ConcurrentHashMap<>();
        String configUrl = System.getenv(ConfigNames.OPENUP_GATEWAY_URL);
        try(Client client = new Client(clients, new URI(configUrl), b -> b)) {
            Map<String, Object> data = Config.getConfigurations(client);
            if(data != null){
                configs.putAll(data);
            }
        } 
        catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
    }
    
    public String getConfig(String name, String def){
        return (String)configs.getOrDefault(name, def);
    }
}
