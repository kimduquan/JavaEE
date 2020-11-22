/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import openup.api.config.ConfigNames;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Config implements openup.api.config.Config {
    
    private Map<String, Object> configs;
    
    @PostConstruct
    void postConstruct(){
        configs = new ConcurrentHashMap<>();
        String url = System.getProperty(ConfigNames.OPENUP_URL);
        if(url == null){
            url = "http://localhost:9080/OpenUP-service-1.0.0/openup/";
        }
        configs.put(ConfigNames.OPENUP_URL, url);
        configs.put(ConfigNames.OPENUP_CONFIG_URL, url + "config");
        configs.put(ConfigNames.OPENUP_SECURITY_URL, url + "security");
        configs.put(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "Authorization");
        configs.put(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "Bearer %s");
        configs.put(ConfigNames.OPENUP_PERSISTENCE_URL, url + "persistence");
    }
    
    
    @Operation(
            summary = "getConfig", 
            description = "getConfig"
    )
    @APIResponse(
            name = "OK", 
            description = "OK",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Map.class)
            )
    )
    @Override
    public Map<String, Object> getConfig() throws Exception {
        return configs;
    }
}
