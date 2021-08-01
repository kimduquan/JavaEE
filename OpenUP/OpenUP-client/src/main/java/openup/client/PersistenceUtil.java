/**
 * 
 */
package openup.client;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.schema.EPF;
import epf.schema.roles.RoleSet;
import openup.schema.OpenUP;
import openup.schema.roles.Role;

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
	
	@GET
	@Path(OpenUP.ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	Role getRole(@MatrixParam("name") final String name);
}
