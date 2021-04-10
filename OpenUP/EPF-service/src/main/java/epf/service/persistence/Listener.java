/**
 * 
 */
package epf.service.persistence;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import epf.schema.PostPersist;
import epf.schema.PostRemove;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Listener {

	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		logger.info(String.format("%s.%s():", getClass().getName(), "postPersist", event.getEntity()));
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		logger.info(String.format("%s.%s():", getClass().getName(), "postRemove", event.getEntity()));
	}
}
