package epf.workflow.spi;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Task;
import epf.workflow.schema.Workflow;

public interface TimeoutService {
	
	Duration getTimeout(final Workflow workflow, final Task task) throws Error;
}
