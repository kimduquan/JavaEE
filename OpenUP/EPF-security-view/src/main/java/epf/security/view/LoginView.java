package epf.security.view;

/**
 * @author PC
 *
 */
public interface LoginView {
	
	/**
	 * 
	 */
	String NAME = "security_login";
	
	/**
	 * @return
	 */
	String getCaller();

	/**
	 * @param caller
	 */
	void setCaller(final String caller);
	
	/**
	 * @param password
	 */
	void setPassword(final char[] password);
	
	/**
	 * @return
	 */
	char[] getPassword();
	
	/**
	 * @return
	 * @throws Exception
	 */
	String login() throws Exception;
}
