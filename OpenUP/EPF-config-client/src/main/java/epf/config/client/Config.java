package epf.config.client;

import java.util.Map;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.CONFIG)
public interface Config {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, String> getProperties(
    		@QueryParam("name") 
    		@DefaultValue("")
    		final String name
    		);
    
    static Map<String, String> getProperties(final Client client, final String name) {
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<Map<String, String>>(){});
    }
}
