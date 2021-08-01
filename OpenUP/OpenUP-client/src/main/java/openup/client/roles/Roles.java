/**
 * 
 */
package openup.client.roles;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import epf.util.client.Client;
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
	String ROLES_URL = "openup.roles.url";

	/**
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<Role> getRoles();
	
	/**
	 * @param client
	 * @return
	 */
	static List<Role> getRoles(final Client client) {
		return client.request(
				target -> target, 
				req -> req)
				.get(new GenericType<List<Role>>() {});
	}
}
