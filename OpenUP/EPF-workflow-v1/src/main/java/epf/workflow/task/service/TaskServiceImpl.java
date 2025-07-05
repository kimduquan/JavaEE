package epf.workflow.task.service;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import epf.workflow.schema.DateTimeDescriptor;
import epf.workflow.schema.Error;
import epf.workflow.schema.ExpressionError;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.schema.TaskDescriptor;
import epf.workflow.schema.ValidationError;
import epf.workflow.schema.WorkflowUtil;
import epf.workflow.schema.util.Either;
import epf.workflow.spi.InputService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.task.call.schema.CallTask;
import epf.workflow.task.event.schema.TaskCompletedEvent;
import epf.workflow.task.event.schema.TaskCreatedEvent;
import epf.workflow.task.event.schema.TaskFaultedEvent;
import epf.workflow.task.event.schema.TaskStartedEvent;
import epf.workflow.task.event.spi.TaskLifecycleEventsService;
import epf.workflow.task.run.schema.RunTask;
import epf.workflow.task.schema.DoTask;
import epf.workflow.task.schema.EmitTask;
import epf.workflow.task.schema.ForTask;
import epf.workflow.task.schema.ForkTask;
import epf.workflow.task.schema.ListenTask;
import epf.workflow.task.schema.RaiseTask;
import epf.workflow.task.schema.SetTask;
import epf.workflow.task.schema.SwitchTask;
import epf.workflow.task.schema.TryTask;
import epf.workflow.task.schema.WaitTask;
import epf.workflow.task.spi.CallService;
import epf.workflow.task.spi.DoService;
import epf.workflow.task.spi.EmitService;
import epf.workflow.task.spi.ForService;
import epf.workflow.task.spi.ForkService;
import epf.workflow.task.spi.ListenService;
import epf.workflow.task.spi.RaiseService;
import epf.workflow.task.spi.RunService;
import epf.workflow.task.spi.SetService;
import epf.workflow.task.spi.SwitchService;
import epf.workflow.task.spi.TaskService;
import epf.workflow.task.spi.TryService;
import epf.workflow.task.spi.WaitService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaskServiceImpl implements TaskService {
	
	@Inject
	transient InputService inputService;
	
	@Inject
	transient TaskLifecycleEventsService taskLifecycleEventsService;
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient CallService callService;
	
	@Inject
	transient DoService doService;
	
	@Inject
	transient EmitService emitService;
	
	@Inject
	transient ForkService forkService;
	
	@Inject
	transient ForService forService;
	
	@Inject
	transient ListenService listenService;
	
	@Inject
	transient RaiseService raiseService;
	
	@Inject
	transient RunService runService;
	
	@Inject
	transient SetService setService;
	
	@Inject
	transient SwitchService switchService;
	
	@Inject
	transient TryService tryService;
	
	@Inject
	transient WaitService waitService;

	@Override
	public Object start(final RuntimeExpressionArguments arguments, final String taskName, final URI taskURI, final Task task, Object taskInput, final AtomicReference<String> flowDirective) throws Error {
		taskInput = validateTaskInput(arguments, task, taskInput);
		fireTaskCreatedEvent(arguments, taskURI);
		Object taskOutput = null;
		if(checkTaskShouldBeStart(task, arguments)) {
			final Instant taskStartedAt = Instant.now();
			fireTaskStartedEvent(arguments, taskURI, taskStartedAt);
			final TaskDescriptor taskDescriptor = createTaskDescriptor(taskName, taskURI, task, taskStartedAt, taskInput);
			arguments.setTask(taskDescriptor);
			taskOutput = doTask(arguments, taskURI, task, taskInput, flowDirective);
		}
		fireTaskCompletedEvent(arguments, taskURI);
		return taskOutput;
	}

	private Object doTask(final RuntimeExpressionArguments arguments, final URI taskURI, final Task task, Object taskInput, final AtomicReference<String> flowDirective) throws RuntimeError, Error {
		try {
			Object taskOutput = null;
			if(task instanceof CallTask) {
				final CallTask callTask = (CallTask) task;
				taskOutput = callService.call(arguments, callTask, taskInput);
			}
			else if(task instanceof DoTask) {
				final DoTask doTask = (DoTask) task;
				taskOutput = doService.do_(doTask.getDo_(), arguments, taskURI, flowDirective);
			}
			else if(task instanceof EmitTask) {
				final EmitTask emitTask = (EmitTask) task;
				taskOutput = emitService.emit(arguments, emitTask, taskInput);
			}
			else if(task instanceof ForkTask) {
				final ForkTask forkTask = (ForkTask) task;
				taskOutput = forkService.fork(arguments, forkTask, flowDirective);
			}
			else if(task instanceof ForTask) {
				final ForTask forTask = (ForTask) task;
				taskOutput = forService._for(arguments, forTask, taskInput, flowDirective);
			}
			else if(task instanceof ListenTask) {
				final ListenTask listenTask = (ListenTask) task;
				taskOutput = listenService.listen(arguments, listenTask, taskInput);
			}
			else if(task instanceof RaiseTask) {
				final RaiseTask raiseTask = (RaiseTask) task;
				taskOutput = raiseService.raise(arguments, raiseTask, taskInput);
			}
			else if(task instanceof RunTask) {
				final RunTask runTask = (RunTask) task;
				taskOutput = runService.run(arguments, runTask, taskInput);
			}
			else if(task instanceof SetTask) {
				final SetTask setTask = (SetTask) task;
				taskOutput = setService.set(arguments, setTask, taskInput);
			}
			else if(task instanceof SwitchTask) {
				final SwitchTask switchTask = (SwitchTask) task;
				taskOutput = switchService._switch(arguments, switchTask, taskInput, flowDirective);
			}
			else if(task instanceof TryTask) {
				final TryTask tryTask = (TryTask) task;
				taskOutput = tryService._try(arguments, tryTask, taskInput, flowDirective);
			}
			else if(task instanceof WaitTask) {
				final WaitTask waitTask = (WaitTask) task;
				taskOutput = waitService.wait(arguments, waitTask, taskInput);
			}
			return taskOutput;
		}
		catch(Error error) {
			fireTaskFaultedEvent(arguments, taskURI, error);
			throw error;
		}
	}

	private void fireTaskCompletedEvent(final RuntimeExpressionArguments arguments, final URI taskURI) throws RuntimeError {
		final Date completedAt = Date.from(Instant.now());
		final TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent();
		taskCompletedEvent.setTask(taskURI);
		taskCompletedEvent.setCompletedAt(completedAt);
		taskCompletedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskCompletedEvent);
	}

	private void fireTaskFaultedEvent(final RuntimeExpressionArguments arguments, final URI taskURI, final Error error) throws RuntimeError {
		final Date faultedAt = Date.from(Instant.now());
		final TaskFaultedEvent taskFaultedEvent = new TaskFaultedEvent();
		taskFaultedEvent.setError(error);
		taskFaultedEvent.setFaultedAt(faultedAt);
		taskFaultedEvent.setTask(taskURI);
		taskFaultedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskFaultedEvent);
	}

	private TaskDescriptor createTaskDescriptor(final String taskName, final URI taskURI, final Task task, final Instant taskStartedAt, final Object taskInput) {
		final DateTimeDescriptor dateTimeDescriptor = DateTimeDescriptor.from(taskStartedAt);
		final TaskDescriptor taskDescriptor = new TaskDescriptor();
		taskDescriptor.setDefinition(task);
		taskDescriptor.setInput(taskInput);
		taskDescriptor.setName(taskName);
		taskDescriptor.setReference(taskURI.toString());
		taskDescriptor.setStartedAt(dateTimeDescriptor);
		return taskDescriptor;
	}

	private void fireTaskStartedEvent(final RuntimeExpressionArguments arguments, final URI taskURI, final Instant taskStartedAt) throws RuntimeError {
		final TaskStartedEvent taskStartedEvent = new TaskStartedEvent();
		taskStartedEvent.setStartedAt(Date.from(taskStartedAt));
		taskStartedEvent.setTask(taskURI);
		taskStartedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskStartedEvent);
	}

	private Object validateTaskInput(final RuntimeExpressionArguments arguments, Task task, Object taskInput) throws ValidationError, ExpressionError {
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

	private void fireTaskCreatedEvent(final RuntimeExpressionArguments arguments, final URI taskURI) throws RuntimeError {
		final Instant taskCreatedAt = Instant.now();
		final TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
		taskCreatedEvent.setCreatedAt(Date.from(taskCreatedAt));
		taskCreatedEvent.setTask(taskURI);
		taskCreatedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskCreatedEvent);
	}

	private boolean checkTaskShouldBeStart(final Task task, final RuntimeExpressionArguments arguments) throws ExpressionError {
		boolean shouldBeRunTask = true;
		if(task.getIf_() != null) {
			shouldBeRunTask = runtimeExpressionsService.if_(task.getIf_(), arguments.getContext(), arguments.getSecrets());
		}
		return shouldBeRunTask;
	}
}
