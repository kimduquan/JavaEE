/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.config;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class ConfigSource {
    
    private Map<String, Object> configs;
    
    @PostConstruct
    void postConstruct(){
        configs = new ConcurrentHashMap<>();
        String configUrl = System.getenv(ConfigNames.OPENUP_GATEWAY_URL);
        try {
            Map<String, Object> data = RestClientBuilder
                    .newBuilder()
                    .baseUrl(new URL(configUrl))
                    .build(Config.class)
                    .getConfigurations();
            if(data != null){
                configs.putAll(data);
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(ConfigSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getConfig(String name, String def){
        return (String)configs.getOrDefault(name, def);
    }
}
