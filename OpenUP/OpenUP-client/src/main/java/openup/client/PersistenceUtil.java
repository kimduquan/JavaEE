/**
 * 
 */
package openup.client;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.schema.EPF;
import epf.schema.roles.RoleSet;

/**
 * @author PC
 *
 */
@RegisterRestClient(configKey = "epf.gateway")
@Path("persistence")
public interface PersistenceUtil extends AutoCloseable {

	@GET
    @Path(EPF.ROLE_SET)
    @Produces(MediaType.APPLICATION_JSON)
	List<RoleSet> getRoleSets();
}
