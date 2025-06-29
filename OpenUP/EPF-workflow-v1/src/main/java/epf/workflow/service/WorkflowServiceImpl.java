package epf.workflow.service;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.ExpressionError;
import epf.workflow.schema.FlowDirective;
import epf.workflow.event.TaskCreatedEvent;
import epf.workflow.event.WorkflowCompletedEvent;
import epf.workflow.event.WorkflowFaultedEvent;
import epf.workflow.event.WorkflowStartedEvent;
import epf.workflow.schema.DateTimeDescriptor;
import epf.workflow.schema.RuntimeDescriptor;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.schema.ValidationError;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.WorkflowDefinitionReference;
import epf.workflow.schema.WorkflowDescriptor;
import epf.workflow.schema.util.Either;
import epf.workflow.schema.util.WorkflowUtil;
import epf.workflow.spi.InputService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.spi.TaskLifecycleEventsService;
import epf.workflow.spi.TaskService;
import epf.workflow.spi.UseService;
import epf.workflow.spi.WorkflowLifecycleEventsService;
import epf.workflow.spi.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowServiceImpl implements WorkflowService {
	
	@Inject
	transient InputService inputService;
	
	@Inject
	transient UseService useService; 
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient WorkflowLifecycleEventsService workflowLifecycleEventsService;
	
	@Inject
	transient TaskLifecycleEventsService taskLifecycleEventsService;
	
	@Inject
	transient TaskService taskService;

	@Override
	public Workflow getWorkflow(final String name, final String version) throws RuntimeError {
		return null;
	}

	@Override
	public Object start(final Object rawInput, final Workflow workflow) throws Error {
		final Instant startedAt = Instant.now();
		final UUID uuid = UUID.randomUUID();
		final String workflowName = WorkflowUtil.getName(workflow.getDocument().getName(), uuid.toString(), workflow.getDocument().getNamespace());
		fireWorkflowStartedEvent(workflow, startedAt, workflowName);
		final RuntimeExpressionArguments arguments = createRuntimeExpressionArguments(rawInput, workflow, startedAt, uuid);
		Object workflowInput = null;
		workflowInput = validateWorkflowInput(rawInput, workflow, arguments, workflowInput);
		try {
			final Object workflowOutput = do_(workflow, workflowInput, arguments);
			fireWorkflowCompletedEvent(workflowName);
			return workflowOutput;
		}
		catch(Error error) {
			fireWorkflowFaultedEvent(workflowName, error);
			throw error;
		}
	}

	private RuntimeExpressionArguments createRuntimeExpressionArguments(final Object rawInput, final Workflow workflow, final Instant startedAt, final UUID uuid) throws Error {
		final DateTimeDescriptor dateTimeDescriptor = DateTimeDescriptor.from(startedAt);
		final WorkflowDescriptor workflowDescriptor = new WorkflowDescriptor();
		workflowDescriptor.setId(uuid.toString());
		workflowDescriptor.setDefinition(workflow);
		workflowDescriptor.setInput(rawInput);
		workflowDescriptor.setStartedAt(dateTimeDescriptor);
		final RuntimeDescriptor runtimeDescriptor = new RuntimeDescriptor();
		final Map<String, Object> context = new HashMap<>();
		final Map<String, Object> secrets = useService.useSecrets(workflow.getUse());
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setRuntime(runtimeDescriptor);
		arguments.setWorkflow(workflowDescriptor);
		return arguments;
	}

	private Object validateWorkflowInput(final Object rawInput, final Workflow workflow, final RuntimeExpressionArguments arguments, Object workflowInput) throws ValidationError, ExpressionError {
		if(workflow.getInput() != null) {
			if(workflow.getInput().getSchema() != null) {
				inputService.validate(rawInput, workflow.getInput());
			}
			if(!Either.isNull(workflow.getInput().getFrom())) {
				workflowInput = runtimeExpressionsService.evaluate(workflow.getInput().getFrom().getLeft(), arguments.getSecrets(), arguments.getWorkflow(), arguments.getRuntime());
			}
		}
		return workflowInput;
	}

	private void fireWorkflowFaultedEvent(final String workflowName, Error error) throws RuntimeError {
		final Date faultedAt = Date.from(Instant.now());
		final WorkflowFaultedEvent workflowFaultedEvent = new WorkflowFaultedEvent();
		workflowFaultedEvent.setError(error);
		workflowFaultedEvent.setFaultedAt(faultedAt);
		workflowFaultedEvent.setName(workflowName);
		workflowLifecycleEventsService.fire(workflowFaultedEvent);
	}

	private void fireWorkflowCompletedEvent(final String workflowName) throws RuntimeError {
		final Date completedAt = Date.from(Instant.now());
		final WorkflowCompletedEvent workflowCompletedEvent = new WorkflowCompletedEvent();
		workflowCompletedEvent.setCompletedAt(completedAt);
		workflowCompletedEvent.setName(workflowName);
		workflowLifecycleEventsService.fire(workflowCompletedEvent);
	}

	private void fireWorkflowStartedEvent(final Workflow workflow, final Instant startedAt, final String workflowName) throws RuntimeError {
		final WorkflowDefinitionReference workflowDefinitionReference = new WorkflowDefinitionReference();
		workflowDefinitionReference.setName(workflow.getDocument().getName());
		workflowDefinitionReference.setNamespace(workflow.getDocument().getNamespace());
		workflowDefinitionReference.setVersion(workflow.getDocument().getVersion());
		final WorkflowStartedEvent workflowStartedEvent = new WorkflowStartedEvent();
		workflowStartedEvent.setDefinition(workflowDefinitionReference);
		workflowStartedEvent.setName(workflowName);
		workflowStartedEvent.setStartedAt(Date.from(startedAt));
		workflowLifecycleEventsService.fire(workflowStartedEvent);
	}

	private Object do_(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments) throws Error {
		Object workflowOutput = null;
		int taskIndex = 0;
		final URI doURI = URI.create("/do");
		final Iterator<Map.Entry<String, Task>> taskIt = workflow.getDo_().entrySet().iterator();
		String taskName;
		Task task;
		URI taskURI;
		Object taskInput;
		Object taskOutput;
		String then;
		final AtomicBoolean end = new AtomicBoolean();
		while(!end.get()) {
			
			taskIndex++;
			final Map.Entry<String, Task> entry = taskIt.next();
			taskName = entry.getKey();
			task = entry.getValue();
			taskURI = doURI.resolve("" + taskIndex).resolve(taskName);
			taskInput = workflowInput;
			
			taskInput = validateTaskInput(arguments, task, taskInput);
			fireTaskCreatedEvent(workflow, arguments, taskURI);
			if(checkTaskShouldBeRun(task, arguments)) {
				taskOutput = taskService.start(workflow, workflowInput, arguments, taskName, taskURI, task, taskInput, end);
				workflowOutput = taskOutput;
			}
			then = task.getThen();
			
			if(!FlowDirective._continue.equals(then) && !FlowDirective._continue.equals(then) && !FlowDirective._continue.equals(then)) {
				taskName = task.getThen();
				task = workflow.getDo_().get(taskName);
				final URI nextTaskURI = taskURI.resolve(taskName);
				taskInput = workflowInput;
				
				taskInput = validateTaskInput(arguments, task, taskInput);
				fireTaskCreatedEvent(workflow, arguments, taskURI);
				if(checkTaskShouldBeRun(task, arguments)) {
					taskOutput = taskService.start(workflow, workflowInput, arguments, taskName, nextTaskURI, task, taskInput, end);
					workflowOutput = taskOutput;
				}
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
