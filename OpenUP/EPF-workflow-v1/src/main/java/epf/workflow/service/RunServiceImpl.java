package epf.workflow.service;

import epf.workflow.spi.ContainerProcessService;
import epf.workflow.spi.RunService;
import epf.workflow.spi.ScriptProcessService;
import epf.workflow.spi.ShellProcessService;
import epf.workflow.spi.WorkflowProcessService;
import epf.workflow.task.schema.Run;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RunServiceImpl implements RunService {
	
	@Inject
	transient ContainerProcessService containerProcessService;
	
	@Inject
	transient ShellProcessService shellProcessService;
	
	@Inject
	transient ScriptProcessService scriptProcessService;
	
	@Inject
	transient WorkflowProcessService workflowProcessService;

	@Override
	public void run(final Run run) throws Exception {
		if(run.getContainer() != null) {
			containerProcessService.run(run.getContainer());
		}
		else if(run.getShell() != null) {
			shellProcessService.run(run.getShell());
		}
		else if(run.getScript() != null) {
			scriptProcessService.run(run.getScript());
		}
		else if(run.getWorkflow() != null) {
			workflowProcessService.run(run.getWorkflow());
		}
	}
}
