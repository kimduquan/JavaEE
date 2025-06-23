package epf.workflow.spi;

import epf.workflow.schema.WorkflowProcess;

public interface WorkflowProcessService {

	void run(final WorkflowProcess workflowProcess) throws Exception;
}
