/**
 * 
 */
package epf.persistence.security.internal.h2;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import epf.persistence.security.internal.SecurityUtil;

/**
 * @author PC
 *
 */
//@RequestScoped
public class H2SecurityUtil implements SecurityUtil {

	/**
	 * 
	 */
	private static final String SET_PASSWORD = "ALTER USER %s SET PASSWORD ?;";
	
	/**
	 * 
	 */
	private H2SecurityUtil() {
		
	}
	
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
