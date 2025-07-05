package epf.workflow.spi;

import epf.workflow.schema.Workflow;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeError;

public interface WorkflowService {

	Workflow getWorkflow(final String name, final String version) throws RuntimeError;
	
	Object start(final Workflow workflow, final Object rawInput) throws Error;
}
