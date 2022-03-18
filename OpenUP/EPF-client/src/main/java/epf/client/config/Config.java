package epf.client.config;

import java.util.Map;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.CONFIG)
public interface Config {
    
    /**
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, String> getProperties(
    		@QueryParam("name") 
    		@DefaultValue("")
    		final String name
    		);
    
    /**
     * @param client
     * @param name
     * @return
     */
    static Map<String, String> getProperties(final Client client, final String name) {
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<Map<String, String>>(){});
    }
}
