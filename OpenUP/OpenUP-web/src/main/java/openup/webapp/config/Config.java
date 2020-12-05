/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.client.Client;
import openup.client.config.ConfigNames;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Config {
    
    private Map<String, Object> configs;
    
    @PostConstruct
    void postConstruct(){
        configs = new ConcurrentHashMap<>();
        String configUrl = System.getenv(ConfigNames.OPENUP_CONFIG_URL);
        Map data = null;
        try(Client client = new Client(configUrl)){
            try(Response response = client
                    .getWebTarget()
                    .request(MediaType.APPLICATION_JSON)
                    .get()){
                data = response.readEntity(Map.class);
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(data != null){
            data.forEach((Object key, Object value) -> {
                configs.put(key.toString(), value);
            });
        }
    }
    
    public String getConfig(String name, String def){
        return (String)configs.getOrDefault(name, def);
    }
}
