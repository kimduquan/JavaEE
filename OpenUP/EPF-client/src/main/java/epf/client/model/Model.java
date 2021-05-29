/**
 * 
 */
package epf.client.model;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.util.client.Client;
import epf.validation.persistence.Unit;

/**
 * @author PC
 *
 */
@Path("model")
public interface Model {
	
	/**
	 * 
	 */
	String EPF_MODEL_URL = "epf.model.url";
	
	/**
     * @return
     */
    @GET
    @Path("{unit}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getEntityTypes(
    		@PathParam("unit")
            @Unit
            @NotBlank
            final String unit);
    
    /**
     * @param client
     * @return
     */
    static List<Entity> getEntityTypes(final Client client, final String unit) {
    	return client
    			.request(
    					target -> target.path(unit), 
    					req -> req.accept(MediaType.APPLICATION_JSON)
    					)
    			.get(new GenericType<List<Entity>>() {});
    }
    
    /**
     * @param unit
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{unit}/{entity}")
    Response getEntityType(
    		@PathParam("unit")
            @Unit
            @NotBlank
            final String unit,
            @PathParam("entity")
            @NotBlank
            final String entity);
    
    /**
     * @param client
     * @param unit
     * @param entity
     * @return
     */
    static Entity getEntityType(final Client client, final String unit, final String entity) {
    	return client
    			.request(
    					target -> target.path(unit).path(entity), 
    					req -> req.accept(MediaType.APPLICATION_JSON)
    					)
    			.get(Entity.class);
    }
}
