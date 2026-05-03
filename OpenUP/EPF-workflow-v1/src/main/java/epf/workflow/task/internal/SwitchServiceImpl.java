package epf.workflow.task.internal;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Switch;
import epf.workflow.schema.SwitchCase;
import epf.workflow.schema.Task;
import epf.workflow.spi.ExtensionService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.task.SwitchService;
import epf.workflow.task.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SwitchServiceImpl implements SwitchService {
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient TaskService taskService;
	
	@Inject
	transient ExtensionService extensionService;

	@Override
	public Object _switch(final RuntimeExpressionArguments arguments, final Switch task, final Object taskInput, final AtomicReference<String> flowDirective) throws Exception {
		SwitchCase switchCase = null;
		String switchCaseName = null;
		for(SwitchCase case_ : task.getSwitch()) {
			if(case_.getWhen() != null) {
				if(runtimeExpressionsService.if_(case_.getWhen(), arguments.getContext(), arguments.getSecrets())) {
					switchCase = case_;
					switchCaseName = case_.getWhen();
					break;
				}
				else {
					continue;
				}
			}
			else {
				switchCase = case_;
				switchCaseName = case_.getWhen();
				break;
			}
		}
		if(switchCase != null && switchCase.getThen() instanceof String) {
			final String caseTaskName = (String) switchCase.getThen();
			final Task caseTask = arguments.getWorkflow().getDefinition().getUse().getFunctions().get(caseTaskName);
			final URI caseTaskURI = URI.create(arguments.getTask().getReference()).resolve(switchCaseName).resolve(caseTaskName);
			flowDirective.set(null);
			Object output = taskInput;
			output = extensionService.before(arguments, caseTaskName, caseTaskURI, caseTask, output, flowDirective);
			output = taskService.start(arguments, caseTaskName, caseTaskURI, caseTask, output, flowDirective);
			output = extensionService.before(arguments, caseTaskName, caseTaskURI, caseTask, output, flowDirective);
			return output;
		}
		return taskInput;
	}

}
