package epf.workflow.task.internal;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.event.TaskLifecycleEventsService;
import epf.workflow.schema.TaskCompletedEvent;
import epf.workflow.schema.TaskCreatedEvent;
import epf.workflow.schema.TaskFaultedEvent;
import epf.workflow.schema.TaskStartedEvent;
import epf.workflow.schema.Try;
import epf.workflow.schema.Wait;
import epf.workflow.schema.Call;
import epf.workflow.schema.DateTimeDescriptor;
import epf.workflow.schema.Do;
import epf.workflow.schema.Emit;
import epf.workflow.schema.Error;
import epf.workflow.schema.For;
import epf.workflow.schema.Fork;
import epf.workflow.schema.Listen;
import epf.workflow.schema.Raise;
import epf.workflow.schema.Run;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Set;
import epf.workflow.schema.Switch;
import epf.workflow.schema.Task;
import epf.workflow.schema.TaskDescriptor;
import epf.workflow.schema.util.Either;
import epf.workflow.spi.InputService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.task.CallService;
import epf.workflow.task.DoService;
import epf.workflow.task.EmitService;
import epf.workflow.task.ForService;
import epf.workflow.task.ForkService;
import epf.workflow.task.ListenService;
import epf.workflow.task.RaiseService;
import epf.workflow.task.RunService;
import epf.workflow.task.SetService;
import epf.workflow.task.SwitchService;
import epf.workflow.task.TaskService;
import epf.workflow.task.TryService;
import epf.workflow.task.WaitService;
import epf.workflow.util.WorkflowUtil;
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
	public Object start(final RuntimeExpressionArguments arguments, final String taskName, final URI taskURI, final Task task, Object taskInput, final AtomicReference<String> flowDirective) throws Exception {
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

	private Object doTask(final RuntimeExpressionArguments arguments, final URI taskURI, final Task task, Object taskInput, final AtomicReference<String> flowDirective) throws Exception {
		try {
			Object taskOutput = null;
			if(task instanceof Call) {
				final Call<?> callTask = (Call<?>) task;
				taskOutput = callService.call(arguments, callTask, taskInput);
			}
			else if(task instanceof Do) {
				final Do doTask = (Do) task;
				taskOutput = doService.do_(doTask.getDo(), arguments, taskURI, flowDirective);
			}
			else if(task instanceof Emit) {
				final Emit emitTask = (Emit) task;
				taskOutput = emitService.emit(arguments, emitTask, taskInput);
			}
			else if(task instanceof Fork) {
				final Fork forkTask = (Fork) task;
				taskOutput = forkService.fork(arguments, forkTask, flowDirective);
			}
			else if(task instanceof For) {
				final For forTask = (For) task;
				taskOutput = forService._for(arguments, forTask, taskInput, flowDirective);
			}
			else if(task instanceof Listen) {
				final Listen listenTask = (Listen) task;
				taskOutput = listenService.listen(arguments, listenTask, taskInput);
			}
			else if(task instanceof Raise) {
				final Raise raiseTask = (Raise) task;
				taskOutput = raiseService.raise(arguments, raiseTask, taskInput);
			}
			else if(task instanceof Run) {
				final Run runTask = (Run) task;
				taskOutput = runService.run(arguments, runTask, taskInput);
			}
			else if(task instanceof Set) {
				final Set setTask = (Set) task;
				taskOutput = setService.set(arguments, setTask, taskInput);
			}
			else if(task instanceof Switch) {
				final Switch switchTask = (Switch) task;
				taskOutput = switchService._switch(arguments, switchTask, taskInput, flowDirective);
			}
			else if(task instanceof Try) {
				final Try tryTask = (Try) task;
				taskOutput = tryService._try(arguments, tryTask, taskInput, flowDirective);
			}
			else if(task instanceof Wait) {
				final Wait waitTask = (Wait) task;
				taskOutput = waitService.wait(arguments, waitTask, taskInput);
			}
			return taskOutput;
		}
		catch(Exception ex) {
			fireTaskFaultedEvent(arguments, taskURI, ex);
			return null;
		}
	}

	private void fireTaskCompletedEvent(final RuntimeExpressionArguments arguments, final URI taskURI) throws Exception {
		final Date completedAt = Date.from(Instant.now());
		final TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent();
		taskCompletedEvent.setTask(taskURI);
		taskCompletedEvent.setCompletedAt(completedAt);
		taskCompletedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskCompletedEvent);
	}

	private void fireTaskFaultedEvent(final RuntimeExpressionArguments arguments, final URI taskURI, final Exception ex) throws Exception {
		final Error error = new Error();
		error.setDetail(ex.getMessage());
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

	private void fireTaskStartedEvent(final RuntimeExpressionArguments arguments, final URI taskURI, final Instant taskStartedAt) throws Exception {
		final TaskStartedEvent taskStartedEvent = new TaskStartedEvent();
		taskStartedEvent.setStartedAt(Date.from(taskStartedAt));
		taskStartedEvent.setTask(taskURI);
		taskStartedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskStartedEvent);
	}

	private Object validateTaskInput(final RuntimeExpressionArguments arguments, Task task, Object taskInput) throws Exception {
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

	private void fireTaskCreatedEvent(final RuntimeExpressionArguments arguments, final URI taskURI) throws Exception {
		final Instant taskCreatedAt = Instant.now();
		final TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
		taskCreatedEvent.setCreatedAt(Date.from(taskCreatedAt));
		taskCreatedEvent.setTask(taskURI);
		taskCreatedEvent.setWorkflow(WorkflowUtil.getName(arguments.getWorkflow().getDefinition(), arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskCreatedEvent);
	}

	private boolean checkTaskShouldBeStart(final Task task, final RuntimeExpressionArguments arguments) throws Exception {
		boolean shouldBeRunTask = true;
		if(task.getIf() != null) {
			shouldBeRunTask = runtimeExpressionsService.if_(task.getIf(), arguments.getContext(), arguments.getSecrets());
		}
		return shouldBeRunTask;
	}
}
