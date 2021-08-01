/**
 * 
 */
package openup.portlet.roles;

import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import openup.client.portlet.Naming;
import openup.client.portlet.roles.RolesView;
import java.io.Serializable;

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

	@Override
	public List<?> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}
}
