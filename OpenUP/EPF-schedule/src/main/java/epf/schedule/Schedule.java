/**
 * 
 */
package epf.schedule;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Schedule {

	/**
	 * 
	 */
	@Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
	private transient ManagedScheduledExecutorService executor;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		executor.shutdownNow();
	}
}
