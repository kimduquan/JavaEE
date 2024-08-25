package epf.security.auth.view;

/**
 * @author PC
 *
 */
public interface AuthView {
	
	/**
	 * @return
	 * @throws Exception
	 */
	String loginWithGoogle() throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	String loginWithFacebook() throws Exception;
}
