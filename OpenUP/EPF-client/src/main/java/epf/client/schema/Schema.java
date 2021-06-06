/**
 * 
 */
package epf.client.schema;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.util.client.Client;
import epf.validation.persistence.Unit;

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
    @Path("{unit}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getEntities(
    		@PathParam("unit")
    		@Unit
            @NotBlank
    		final String unit);
    
    /**
     * @param client
     * @param unit
     * @return
     */
    static Response getEntities(
    		final Client client,
    		final String unit) {
    	return client.request(
    			target -> target.path(unit), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    	.get();
    }
}
