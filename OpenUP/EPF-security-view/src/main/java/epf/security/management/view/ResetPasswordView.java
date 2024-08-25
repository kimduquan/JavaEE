package epf.security.management.view;

/**
 * 
 */
public interface ResetPasswordView {

	/**
	 * @return
	 */
	String getCode();

	/**
	 * @param code
	 */
	 void setCode(final String code);

	/**
	 * @return
	 */
	 String getData();

	/**
	 * @param data
	 */
	 void setData(final String data);

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
	 * @throws Exception
	 */
	String reset() throws Exception;
}
