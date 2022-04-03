package epf.security.view;

import java.util.List;

/**
 * @author PC
 *
 */
public interface PrincipalView {
	
	/**
	 * @return
	 */
	String getName();
	
	/**
	 * @return
	 */
	List<String> getClaimNames();
	
	/**
	 * @param name
	 * @return
	 */
	String getClaim(final String name);
	
	/**
	 * @return
	 */
	String logout() throws Exception;
}
