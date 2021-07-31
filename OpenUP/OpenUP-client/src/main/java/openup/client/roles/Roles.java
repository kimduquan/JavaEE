/**
 * 
 */
package openup.client.roles;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.schema.roles.RoleSet;
import openup.client.OpenUPException;
import openup.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("roles")
public interface Roles {
	
	/**
	 * 
	 */
	String ANY_ROLE = "Any_Role";

	/**
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<RoleSet> getRoles();
	
	/**
	 * @param roleSet
	 * @param role
	 * @return
	 * @throws OpenUPException
	 */
	@GET
	@Path("{roleSet}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	List<Role> getRoles(@PathParam("roleSet") final String roleSet, @PathParam("role") final String role) throws OpenUPException;
	
}
