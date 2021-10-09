/**
 * 
 */
package epf.client.schema;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import epf.client.util.Client;

/**
 * @author PC
 *
 */
@Path("schema")
public interface Schema {

	/**
	 * 
	 */
	String SCHEMA_URL = "epf.schema.url";

    /**
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getEntities();
    
    /**
     * @param client
     * @return
     */
    static Response getEntities(final Client client) {
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    	.get();
    }
    
    /**
     * @return
     */
    @GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
    Response getEmbeddables();
    
    /**
     * @param client
     * @return
     */
    static Response getEmbeddables(final Client client) {
    	return client.request(
    			target -> target.path("embeddable"), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    	.get();
    }
}
