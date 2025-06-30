package epf.workflow.service;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.FlowDirective;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.spi.DoService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.spi.TaskLifecycleEventsService;
import epf.workflow.spi.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DoServiceImpl implements DoService {
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient TaskLifecycleEventsService taskLifecycleEventsService;
	
	@Inject
	transient TaskService taskService;

	@Override
	public Object do_(final Map<String, Task> do_, final RuntimeExpressionArguments arguments, final URI parentURI, final AtomicBoolean end) throws Error {
		Object workflowOutput = null;
		int taskIndex = 0;
		final Iterator<Map.Entry<String, Task>> taskIt = do_.entrySet().iterator();
		String taskName = null;
		Task task = null;
		URI taskURI = null;
		Object taskInput = null;
		Object taskOutput = null;
		String then = null;
		do {
			if(taskIt.hasNext()) {
				taskIndex++;
				final Map.Entry<String, Task> entry = taskIt.next();
				taskName = entry.getKey();
				task = entry.getValue();
				taskURI = parentURI.resolve("" + taskIndex).resolve(taskName);
				taskOutput = taskService.start(arguments, taskName, taskURI, task, taskInput, end);
				workflowOutput = taskOutput;
				then = task.getThen();
				if(FlowDirective._continue.equals(then)) {
					continue;
				}
				else if(FlowDirective.exit.equals(then)) {
					break;
				}
				else if(FlowDirective.end.equals(then)) {
					end.set(true);
					return workflowOutput;
				}
				else {
					taskIndex++;
					taskName = task.getThen();
					task = arguments.getWorkflow().getDefinition().getDo_().get(taskName);
					final URI nextTaskURI = taskURI.resolve(taskName);
					taskOutput = taskService.start(arguments, taskName, nextTaskURI, task, taskOutput, end);
					workflowOutput = taskOutput;
					then = task.getThen();
				}
			}
		}
		while(!end.get());
		return workflowOutput;
	}
}
