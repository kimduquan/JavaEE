package epf.workflow.spi;

import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.WorkflowProcess;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;

public interface WorkflowProcessService {

	ProcessResult run(final WorkflowProcess workflowProcess, final boolean await, final Duration timeout) throws Error;
}
