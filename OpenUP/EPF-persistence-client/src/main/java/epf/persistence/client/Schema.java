package epf.persistence.client;

import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.persistence.schema.Embeddable;
import epf.persistence.schema.Entity;

@Path(Naming.SCHEMA)
public interface Schema {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Entity> getEntities();
    
    static Response getEntities(final Client client) {
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    	.get();
    }
    
    @GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
    List<Embeddable> getEmbeddables();
    
    static Response getEmbeddables(final Client client) {
    	return client.request(
    			target -> target.path("embeddable"), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    	.get();
    }
}
