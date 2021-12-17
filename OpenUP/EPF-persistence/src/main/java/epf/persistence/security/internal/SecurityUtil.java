/**
 * 
 */
package epf.persistence.security.internal;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author PC
 *
 */
public interface SecurityUtil {
	
	/**
	 * @param manager
	 * @param userName
	 * @param password
	 */
	@Transactional
	void setUserPassword(final EntityManager manager, final String userName, final char... password);
}
