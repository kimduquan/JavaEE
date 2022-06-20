package epf.security.view;

/**
 * 
 */
public interface RegistrationView {

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
	 * @throws Exception 
	 */
	String createPrincipal() throws Exception;
}
