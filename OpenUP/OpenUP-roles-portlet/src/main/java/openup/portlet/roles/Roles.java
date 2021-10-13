/**
 * 
 */
package openup.portlet.roles;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import epf.util.logging.Logging;
import openup.client.portlet.Naming;
import openup.client.portlet.roles.RolesView;
import java.io.Serializable;
import openup.schema.OpenUP;
import openup.schema.roles.Role;
import epf.client.persistence.Queries;
import epf.client.util.Client;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.internal.security.SecurityUtil;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.ROLES)
public class Roles implements Serializable, RolesView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Roles.class.getName());
	
	/**
	 * 
	 */
	private Role role;
	
	/**
	 * 
	 */
	private List<Role> roles;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil securityUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = securityUtil.newClient(gatewayUtil.get("persistence"))){
			roles = Queries.executeQuery(
					client, 
					OpenUP.SCHEMA,
					new GenericType<List<Role>>() {}, 
					target -> target.path(OpenUP.ROLE), 
					null, 
					null);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}

	@Override
	public List<?> getRoles() {
		return roles;
	}

	@Override
	public void setRole(final Object role) {
		this.role = (Role) role;
	}

	@Override
	public String toString(final Object role) {
		return role.toString();
	}

	public Object getRole() {
		return role;
	}
}
