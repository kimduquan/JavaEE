package epf.workflow.internal;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Task;
import epf.workflow.schema.Timeout;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.TimeoutService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeoutServiceImpl implements TimeoutService {

	@Override
	public Duration getTimeout(final Workflow workflow, final Task task) throws Error {
		if(task.getTimeout().isLeft()) {
			final String timeoutName = task.getTimeout().getLeft();
			final Timeout timeout = workflow.getUse().getTimeouts().get(timeoutName);
			return timeout.getAfter();
		}
		else if(task.getTimeout().isRight()) {
			return task.getTimeout().getRight().getAfter();
		}
		return null;
	}
}
