/**
 * 
 */
package epf.client.portlet.security;

/**
 * @author PC
 *
 */
public interface PrincipalView {
	/**
	 * @return
	 */
	char[] getPassword();
	/**
	 * @param password
	 */
	void setPassword(final char... password);
	
	/**
	 * @return
	 * @throws Exception
	 */
	String logout() throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	String update() throws Exception;
	
	/**
	 * @throws Exception
	 */
	void revoke() throws Exception;
	
	/**
	 * @return
	 */
	String getFullName();
}
