/**
 * 
 */
package epf.persistence.security;

import java.util.Set;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author PC
 *
 */
public interface SecurityService {

	/**
	 * @param manager
	 * @param userName
	 * @return
	 */
	Set<String> getUserRoles(final EntityManager manager, final String userName);
	
	/**
	 * @param manager
	 * @param userName
	 * @param password
	 */
	@Transactional
	void setUserPassword(final EntityManager manager, final String userName, final char... password);
}
