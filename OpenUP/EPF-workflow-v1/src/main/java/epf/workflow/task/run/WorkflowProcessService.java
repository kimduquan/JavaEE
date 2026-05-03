package epf.workflow.task.run;

import epf.workflow.schema.WorkflowProcess;
import epf.workflow.schema.Duration;

public interface WorkflowProcessService {

	Object run(final WorkflowProcess workflowProcess, final boolean await, final Duration timeout) throws Exception;
}
