package epf.workflow.service;

import epf.workflow.schema.Workflow;
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
	public void run(final WorkflowProcess workflowProcess) throws Exception {
		final Workflow workflow = workflowService.getWorkflow(workflowProcess.getName(), workflowProcess.getVersion());
		workflowService.start(workflow);
	}
}
