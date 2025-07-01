package epf.workflow.service;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.FlowDirective;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.SwitchCase;
import epf.workflow.schema.Task;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.spi.SwitchService;
import epf.workflow.spi.TaskService;
import epf.workflow.task.schema.SwitchTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SwitchServiceImpl implements SwitchService {
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient TaskService taskService;

	@Override
	public Object _switch(final RuntimeExpressionArguments arguments, final SwitchTask task, final Object taskInput, final AtomicBoolean end) throws Error {
		SwitchCase switchCase = task.getSwitch_().get(SwitchCase.DEFAULT);
		String switchCaseName = null;
		for(Map.Entry<String, SwitchCase> entry : task.getSwitch_().entrySet()) {
			final String caseName = entry.getKey();
			final SwitchCase case_ = entry.getValue();
			if(case_.getWhen() != null) {
				if(runtimeExpressionsService.if_(case_.getWhen(), arguments.getContext(), arguments.getSecrets())) {
					switchCase = case_;
					switchCaseName = caseName;
					break;
				}
				else {
					continue;
				}
			}
			else {
				switchCase = case_;
				switchCaseName = caseName;
				break;
			}
		}
		if(switchCase != null) {
			if(FlowDirective._continue.equals(switchCase.getThen()) || FlowDirective.exit.equals(switchCase.getThen())) {
				return null;
			}
			else if(FlowDirective.end.equals(switchCase.getThen())) {
				end.set(true);
				return null;
			}
			else {
				final String caseTaskName = switchCase.getThen();
				final Task caseTask = arguments.getWorkflow().getDefinition().getDo_().get(caseTaskName);
				final URI caseTaskURI = URI.create(arguments.getTask().getReference()).resolve(switchCaseName).resolve(caseTaskName);
				return taskService.start(arguments, caseTaskName, caseTaskURI, caseTask, taskInput, end);
			}
		}
		return null;
	}

}
