package epf.persistence.schema.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.naming.Naming;
import io.smallrye.mutiny.Multi;

/**
 * @author PC
 *
 */
@Path(Naming.SCHEMA)
public interface RxSchema {

	/**
	 * @return
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    Multi<Entity> getEntities();
	
	/**
	 * @return
	 */
	@GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
	Multi<Embeddable> getEmbeddables();
}
