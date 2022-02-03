package epf.persistence.internal.rx;

import java.util.Map;
import javax.persistence.Persistence;
import epf.persistence.util.EntityManagerFactory;

/**
 * @author PC
 *
 */
public interface RxPersistence {

	/**
	 * @param unit
	 * @param props
	 * @return
	 */
	static EntityManagerFactory createEntityManagerFactory(final String unit, final Map<String, Object> props) {
		return new RxEntityManagerFactory(Persistence.createEntityManagerFactory(unit, props));
	}
}
