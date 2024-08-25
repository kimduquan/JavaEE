package epf.security.view;

/**
 * 
 */
public interface RegisterView {

	/**
	 * @return
	 */
	String getEmail();
	
	/**
	 * @param email
	 */
	void setEmail(final String email);
	
	/**
	 * @return
	 */
	String getFirstName();
	
	/**
	 * @param firstName
	 */
	void setFirstName(final String firstName);
	
	/**
	 * @return
	 */
	String getLastName();
	
	/**
	 * @param lastName
	 */
	void setLastName(final String lastName);
	
	/**
	 * @return
	 */
	String getPassword();
	
	/**
	 * @param password
	 */
	void setPassword(final String password);
	
	/**
	 * @return
	 */
	String getRepeatPassword();
	
	/**
	 * @param repeatPassword
	 */
	void setRepeatPassword(final String repeatPassword);
	
	/**
	 * @return
	 */
	String register() throws Exception;
}
