/**
 * 
 */
package openup.roles;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.schema.roles.RoleSet;
import openup.client.OpenUPException;
import openup.client.PersistenceUtil;
import openup.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(openup.client.roles.Roles.ANY_ROLE)
@RequestScoped
public class Roles implements openup.client.roles.Roles {
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	private transient PersistenceUtil persistence;

	@Override
	public List<RoleSet> getRoles() {
		return persistence.getRoleSets();
	}

	@Override
	public List<Role> getRoles(final String roleSet, final String role) throws OpenUPException {
		// TODO Auto-generated method stub
		return null;
	}
}
