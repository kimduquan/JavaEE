package epf.workflow.task.run.spi;

import epf.workflow.schema.WorkflowProcess;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;

public interface WorkflowProcessService {

	Object run(final WorkflowProcess workflowProcess, final boolean await, final Duration timeout) throws Error;
}
