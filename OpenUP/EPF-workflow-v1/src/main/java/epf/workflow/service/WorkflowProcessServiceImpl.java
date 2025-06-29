package epf.workflow.service;

import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.WorkflowProcess;
import epf.workflow.spi.WorkflowProcessService;
import epf.workflow.spi.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowProcessServiceImpl implements WorkflowProcessService {
	
	@Inject
	transient WorkflowService workflowService;

	@Override
	public ProcessResult run(final WorkflowProcess workflowProcess, final boolean await, final Duration timeout) throws Error {
		final Workflow workflow = workflowService.getWorkflow(workflowProcess.getName(), workflowProcess.getVersion());
		workflowService.start(workflowProcess.getInput(), workflow);
		final ProcessResult processResult = new ProcessResult();
		return processResult;
	}
}
