package epf.workflow.spi;

import epf.workflow.schema.Workflow;

public interface WorkflowService {

	Workflow getWorkflow(final String name, final String version) throws Exception;
	
	void start(final Workflow workflow) throws Exception;
}
