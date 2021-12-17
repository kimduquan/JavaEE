/**
 * 
 */
package epf.persistence.security.internal.mysql;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import epf.persistence.security.internal.SecurityUtil;

/**
 * @author PC
 *
 */
@RequestScoped
public class MySQLSecurityUtil implements SecurityUtil {

	/**
	 * 
	 */
	private static final String SET_PASSWORD = "SET PASSWORD = ?;";
	
	@Override
	@Transactional
	public void setUserPassword(final EntityManager manager, final String userName, final char... password) {
		if(!manager.isJoinedToTransaction()) {
			manager.joinTransaction();
		}
		final String query = String.format(SET_PASSWORD, userName);
		manager
		.createNativeQuery(query)
		.setParameter(1, new String(password))
		.executeUpdate();
		manager.flush();
	}
}
