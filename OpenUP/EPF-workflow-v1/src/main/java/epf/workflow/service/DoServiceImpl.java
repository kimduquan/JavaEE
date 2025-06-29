package epf.workflow.service;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.FlowDirective;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.schema.Workflow;
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
	public Object do_(final Workflow workflow, final Map<String, Task> do_, final Object workflowInput, final RuntimeExpressionArguments arguments, final URI parentURI) throws Error {
		Object workflowOutput = null;
		int taskIndex = 0;
		final Iterator<Map.Entry<String, Task>> taskIt = do_.entrySet().iterator();
		String taskName;
		Task task;
		URI taskURI;
		Object taskInput;
		Object taskOutput = null;
		String then;
		final AtomicBoolean end = new AtomicBoolean();
		while(!end.get()) {
			taskIndex++;
			final Map.Entry<String, Task> entry = taskIt.next();
			taskName = entry.getKey();
			task = entry.getValue();
			taskURI = parentURI.resolve("" + taskIndex).resolve(taskName);
			taskInput = workflowInput;
			taskOutput = taskService.start(workflow, workflowInput, arguments, taskName, taskURI, task, taskInput, end);
			workflowOutput = taskOutput;
			then = task.getThen();
			if(!FlowDirective._continue.equals(then) && !FlowDirective._continue.equals(then) && !FlowDirective._continue.equals(then)) {
				taskName = task.getThen();
				task = workflow.getDo_().get(taskName);
				final URI nextTaskURI = taskURI.resolve(taskName);
				taskInput = workflowInput;
				taskOutput = taskService.start(workflow, workflowInput, arguments, taskName, nextTaskURI, task, taskInput, end);
				workflowOutput = taskOutput;
				then = task.getThen();
			}
			if(FlowDirective._continue.equals(then)) {
				continue;
			}
			else if(FlowDirective.exit.equals(then)) {
				break;
			}
			else if(FlowDirective.end.equals(then)) {
				return workflowOutput;
			}
		}
		return workflowOutput;
	}
}
