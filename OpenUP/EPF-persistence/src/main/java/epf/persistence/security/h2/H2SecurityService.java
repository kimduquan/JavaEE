/**
 * 
 */
package epf.persistence.security.h2;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import epf.schema.h2.Queries;
import epf.persistence.security.SecurityService;

/**
 * @author PC
 *
 */
@RequestScoped
public class H2SecurityService implements SecurityService {

	@Override
	@Transactional
	public void setUserPassword(final EntityManager manager, final String userName, final char... password) {
		if(!manager.isJoinedToTransaction()) {
			manager.joinTransaction();
		}
		final String query = String.format(Queries.SET_PASSWORD, userName);
		manager
		.createNativeQuery(query)
		.setParameter(1, new String(password))
		.executeUpdate();
		manager.flush();
	}
}
