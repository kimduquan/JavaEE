/**
 * 
 */
package epf.client.persistence.metamodel;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.HEAD;
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
@Path("metamodel")
public interface Entities {
	/**
     * @return
     */
    @HEAD
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
    static List<EntityType> getEntityTypes(final Client client, final String unit) {
    	return client
    			.request(
    					target -> target.path(unit), 
    					req -> req.accept(MediaType.APPLICATION_JSON)
    					)
    			.head().readEntity(new GenericType<List<EntityType>>() {});
    }
    
    /**
     * @param unit
     * @param name
     * @return
     */
    @HEAD
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
    static EntityType getEntityType(final Client client, final String unit, final String entity) {
    	return client
    			.request(
    					target -> target.path(unit).path(entity), 
    					req -> req.accept(MediaType.APPLICATION_JSON_TYPE)
    					)
    			.head().readEntity(EntityType.class);
    }
}
