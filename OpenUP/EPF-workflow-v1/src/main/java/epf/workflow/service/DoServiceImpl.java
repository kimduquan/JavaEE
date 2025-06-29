package epf.workflow.service;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.event.TaskCreatedEvent;
import epf.workflow.schema.Error;
import epf.workflow.schema.ExpressionError;
import epf.workflow.schema.FlowDirective;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.schema.ValidationError;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.util.Either;
import epf.workflow.schema.util.WorkflowUtil;
import epf.workflow.spi.DoService;
import epf.workflow.spi.InputService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.spi.TaskLifecycleEventsService;
import epf.workflow.spi.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DoServiceImpl implements DoService {
	
	@Inject
	transient InputService inputService;
	
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
			taskOutput = doTask(workflow, workflowInput, arguments, taskName, task, taskURI, taskInput, taskOutput, end);
			workflowOutput = taskOutput;
			then = task.getThen();
			if(!FlowDirective._continue.equals(then) && !FlowDirective._continue.equals(then) && !FlowDirective._continue.equals(then)) {
				taskName = task.getThen();
				task = workflow.getDo_().get(taskName);
				final URI nextTaskURI = taskURI.resolve(taskName);
				taskInput = workflowInput;
				taskOutput = doTask(workflow, workflowInput, arguments, taskName, task, nextTaskURI, taskInput, taskOutput, end);
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

	private Object doTask(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final Task task, URI taskURI, Object taskInput, Object taskOutput, final AtomicBoolean end) throws ValidationError, ExpressionError, RuntimeError, Error {
		taskInput = validateTaskInput(arguments, task, taskInput);
		fireTaskCreatedEvent(workflow, arguments, taskURI);
		if(checkTaskShouldBeRun(task, arguments)) {
			taskOutput = taskService.start(workflow, workflowInput, arguments, taskName, taskURI, task, taskInput, end);
		}
		return taskOutput;
	}

	private Object validateTaskInput(final RuntimeExpressionArguments arguments, Task task, Object taskInput)
			throws ValidationError, ExpressionError {
		if(task.getInput() != null) {
			if(task.getInput().getSchema() != null) {
				inputService.validate(taskInput, task.getInput());
			}
			if(!Either.isNull(task.getInput().getFrom())) {
				taskInput = runtimeExpressionsService.evaluate(task.getInput().getFrom().getLeft(), arguments.getSecrets(), arguments.getWorkflow(), arguments.getRuntime());
			}
		}
		return taskInput;
	}

	private void fireTaskCreatedEvent(final Workflow workflow, final RuntimeExpressionArguments arguments, final URI taskURI) throws RuntimeError {
		final Instant taskCreatedAt = Instant.now();
		final TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
		taskCreatedEvent.setCreatedAt(Date.from(taskCreatedAt));
		taskCreatedEvent.setTask(taskURI);
		taskCreatedEvent.setWorkflow(WorkflowUtil.getName(workflow, arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskCreatedEvent);
	}

	private boolean checkTaskShouldBeRun(final Task task, final RuntimeExpressionArguments arguments) throws ExpressionError {
		boolean shouldBeRunTask = true;
		if(task.getIf_() != null) {
			shouldBeRunTask = runtimeExpressionsService.if_(task.getIf_(), arguments.getContext(), arguments.getSecrets());
		}
		return shouldBeRunTask;
	}
}
