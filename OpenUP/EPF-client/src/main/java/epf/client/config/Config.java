/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.config;

import java.util.Map;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import epf.schema.EPF;
import epf.util.client.Client;

/**
 *
 * @author FOXCONN
 */
@Path("config")
public interface Config {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, String> getProperties(
    		@QueryParam("name") 
    		@DefaultValue(EPF.Schema)
    		String name
    		) throws Exception;
    
    static Map<String, String> getProperties(Client client, String name) throws Exception{
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<Map<String, String>>(){});
    }
}
