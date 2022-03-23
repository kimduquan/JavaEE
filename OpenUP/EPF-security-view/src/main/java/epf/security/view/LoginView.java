package epf.security.view;

/**
 * @author PC
 *
 */
public interface LoginView {
	
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
	 */
	boolean isRememberMe();
	
	/**
	 * @param rememberMe
	 */
	void setRememberMe(final boolean rememberMe);
	
	/**
	 * @return
	 * @throws Exception
	 */
	String login() throws Exception;
}
