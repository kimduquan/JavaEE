package epf.security.view;

/**
 * 
 */
public interface ForgotPasswordView {

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
	 * @throws Exception
	 */
	String reset() throws Exception;
}
