/**
 * 
 */
package openup.client.portlet.roles;

import java.util.List;

/**
 * @author PC
 *
 */
public interface RolesView {

	/**
	 * @return
	 */
	List<?> getRoles();
	
	/**
	 * @param role
	 */
	void setRole(final Object role);
	
	/**
	 * @return
	 */
	Object getRole();
	
	/**
	 * @param role
	 * @return
	 */
	String toString(final Object role);
}
