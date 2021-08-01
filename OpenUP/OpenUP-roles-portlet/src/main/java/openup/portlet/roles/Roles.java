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

import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import openup.client.portlet.Naming;
import openup.client.portlet.roles.RolesView;
import java.io.Serializable;
import openup.schema.roles.Role;

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
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient RegistryUtil registry;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = clientUtil.newClient(registry.get("roles"))){
			roles = openup.client.roles.Roles.getRoles(client);
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

	@Override
	public Object getRole() {
		return role;
	}
}
