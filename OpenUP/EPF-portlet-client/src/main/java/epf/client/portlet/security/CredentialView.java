/**
 * 
 */
package epf.client.portlet.security;

/**
 * @author PC
 *
 */
public interface CredentialView {

	/**
	 * @return
	 */
	String getCaller();
	/**
	 * @param caller
	 */
	void setCaller(final String caller);
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
	String login() throws Exception;
}
