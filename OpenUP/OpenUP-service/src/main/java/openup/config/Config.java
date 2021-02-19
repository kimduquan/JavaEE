/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import epf.client.config.ConfigNames;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Path("config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Config implements epf.client.config.Config {
    
    private Map<String, Object> configs;
    
    @PostConstruct
    void postConstruct(){
        configs = new ConcurrentHashMap<>();
        String url = System.getenv(ConfigNames.GATEWAY_URL);
        configs.put(ConfigNames.GATEWAY_URL, url);
        configs.put(ConfigNames.PERSISTENCE_URL, url + "persistence");
        configs.put(ConfigNames.SECURITY_URL, url + "security");
    }
    
    @Override
    @PermitAll
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
    public Map<String, Object> getConfigurations() throws Exception {
        return configs;
    }
}
