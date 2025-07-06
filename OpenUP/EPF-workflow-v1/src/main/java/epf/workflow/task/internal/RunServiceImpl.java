package epf.workflow.task.internal;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.TimeoutService;
import epf.workflow.task.RunService;
import epf.workflow.task.run.ContainerProcessService;
import epf.workflow.task.run.ScriptProcessService;
import epf.workflow.task.run.ShellProcessService;
import epf.workflow.task.run.WorkflowProcessService;
import epf.workflow.task.run.schema.ProcessResult;
import epf.workflow.task.run.schema.RunTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RunServiceImpl implements RunService {
	
	@Inject
	transient TimeoutService timeoutService;
	
	@Inject
	transient ContainerProcessService containerProcessService;
	
	@Inject
	transient ShellProcessService shellProcessService;
	
	@Inject
	transient ScriptProcessService scriptProcessService;
	
	@Inject
	transient WorkflowProcessService workflowProcessService;
	
	@Override
	public Object run(final RuntimeExpressionArguments arguments, final RunTask task, final Object taskInput) throws Error {
		final Duration timeout = timeoutService.getTimeout(arguments.getWorkflow().getDefinition(), task);
		ProcessResult processResult = null;
		if(task.getRun().getContainer() != null) {
			processResult = containerProcessService.run(task.getRun().getContainer(), arguments, task.isAwait(), timeout);
		}
		else if(task.getRun().getShell() != null) {
			processResult = shellProcessService.run(task.getRun().getShell(), task.isAwait(), timeout);
		}
		else if(task.getRun().getScript() != null) {
			return scriptProcessService.run(task.getRun().getScript(), task.isAwait(), timeout);
		}
		else if(task.getRun().getWorkflow() != null) {
			return workflowProcessService.run(task.getRun().getWorkflow(), task.isAwait(), timeout);
		}
		Object taskOutput;
		switch(task.getReturn_()) {
			case all:
				taskOutput = processResult;
				break;
			case code:
				taskOutput = processResult.getCode();
				break;
			case none:
				taskOutput = null;
				break;
			case stderr:
				taskOutput = processResult.getStderr();
				break;
			case stdout:
				taskOutput = processResult.getStdout();
				break;
			default:
				taskOutput = null;
				break;
		}
		return taskOutput;
	}
}
