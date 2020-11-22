/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import openup.api.config.ConfigNames;

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
        String configUrl = System.getProperty(ConfigNames.OPENUP_CONFIG_URL);
        Client client = ClientBuilder.newClient();
        Map data = client.target(configUrl)
                .request(MediaType.APPLICATION_JSON)
                .get(Map.class);
        client.close();
        data.forEach((Object key, Object value) -> {
            configs.put(key.toString(), value);
        });
    }
    
    public Object getConfig(String name, Object def){
        return configs.getOrDefault(name, def);
    }
}
