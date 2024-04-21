package epf.workflow.states.sleep;

import java.time.Duration;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
public class WorkflowSleepStates {

	public void sleep(final Duration duration) throws Exception {
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
	}
}
