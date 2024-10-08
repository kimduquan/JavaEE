package epf.persistence.client;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.persistence.schema.Embeddable;
import epf.persistence.schema.Entity;

/**
 * @author PC
 *
 */
@Path(Naming.SCHEMA)
public interface Schema {

    /**
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Entity> getEntities();
    
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
    List<Embeddable> getEmbeddables();
    
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
