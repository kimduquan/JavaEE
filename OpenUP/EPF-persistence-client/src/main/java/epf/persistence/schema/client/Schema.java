package epf.persistence.schema.client;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.client.util.Client;
import epf.naming.Naming;

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
    CompletionStage<List<Entity>> getEntities(@Context final SecurityContext context);
    
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
    CompletionStage<List<Embeddable>> getEmbeddables(@Context final SecurityContext context);
    
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
