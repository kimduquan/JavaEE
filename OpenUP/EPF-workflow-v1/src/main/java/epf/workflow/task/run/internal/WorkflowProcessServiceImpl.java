package epf.workflow.task.run.internal;

import epf.workflow.schema.Workflow;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.WorkflowProcess;
import epf.workflow.spi.WorkflowService;
import epf.workflow.task.run.WorkflowProcessService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowProcessServiceImpl implements WorkflowProcessService {
	
	@Inject
	transient WorkflowService workflowService;

	@Override
	public Object run(final WorkflowProcess workflowProcess, final boolean await, final Duration timeout) throws Error {
		final Workflow workflow = workflowService.getWorkflow(workflowProcess.getName(), workflowProcess.getVersion());
		return workflowService.start(workflow, workflowProcess.getInput());
	}
}
