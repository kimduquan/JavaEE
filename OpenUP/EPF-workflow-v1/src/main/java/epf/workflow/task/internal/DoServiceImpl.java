package epf.workflow.task.internal;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.event.TaskLifecycleEventsService;
import epf.workflow.schema.Error;
import epf.workflow.schema.FlowDirective;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.spi.ExtensionService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.task.DoService;
import epf.workflow.task.TaskService;
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
	
	@Inject
	transient ExtensionService extensionService; 

	@Override
	public Object do_(final Map<String, Task> do_, final RuntimeExpressionArguments arguments, final URI parentURI, final AtomicReference<String> flowDirective) throws Error {
		int taskIndex = 0;
		final Iterator<Map.Entry<String, Task>> taskIt = do_.entrySet().iterator();
		String taskName = null;
		Task task = null;
		URI taskURI = null;
		Object output = null;
		String then = null;
		while(taskIt.hasNext()) {
			final Map.Entry<String, Task> entry = taskIt.next();
			taskIndex++;
			taskName = entry.getKey();
			task = entry.getValue();
			taskURI = parentURI.resolve("" + taskIndex).resolve(taskName);
			output = arguments.getTask().getInput();
			output = extensionService.before(arguments, taskName, taskURI, task, output, flowDirective);
			output = taskService.start(arguments, taskName, taskURI, task, output, flowDirective);
			output = extensionService.after(arguments, taskName, taskURI, task, output, flowDirective);
			then = task.getThen();
			if(FlowDirective._continue.equals(then)) {
				continue;
			}
			else if(FlowDirective.isString(then)) {
				taskIndex++;
				final String nextTaskName = task.getThen();
				final Task nextTask = arguments.getWorkflow().getDefinition().getUse().getFunctions().get(nextTaskName);
				final URI nextTaskURI = taskURI.resolve(nextTaskName);
				flowDirective.set(null);
				output = extensionService.before(arguments, nextTaskName, nextTaskURI, nextTask, output, flowDirective);
				output = taskService.start(arguments, nextTaskName, nextTaskURI, nextTask, output, flowDirective);
				output = extensionService.after(arguments, nextTaskName, nextTaskURI, nextTask, output, flowDirective);
				then = nextTask.getThen();
			}
			else {
				break;
			}
		}
		return output;
	}
}
