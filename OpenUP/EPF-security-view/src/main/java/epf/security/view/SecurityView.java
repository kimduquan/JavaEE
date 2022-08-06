package epf.security.view;

import java.util.List;

/**
 * @author PC
 *
 */
public interface SecurityView {
	
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
	 * @param url
	 * @return
	 * @throws Exception
	 */
	String login(final String url) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	String authenticate() throws Exception;
	
	/**
	 * @return
	 */
	String logout() throws Exception;
}
