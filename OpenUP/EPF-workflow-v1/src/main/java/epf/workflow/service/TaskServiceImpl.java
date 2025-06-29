package epf.workflow.service;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.event.TaskCompletedEvent;
import epf.workflow.event.TaskFaultedEvent;
import epf.workflow.event.TaskStartedEvent;
import epf.workflow.schema.DateTimeDescriptor;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.schema.TaskDescriptor;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.util.WorkflowUtil;
import epf.workflow.spi.CallService;
import epf.workflow.spi.DoService;
import epf.workflow.spi.EmitService;
import epf.workflow.spi.ForService;
import epf.workflow.spi.ForkService;
import epf.workflow.spi.ListenService;
import epf.workflow.spi.RaiseService;
import epf.workflow.spi.RunService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.spi.SetService;
import epf.workflow.spi.SwitchService;
import epf.workflow.spi.TaskLifecycleEventsService;
import epf.workflow.spi.TaskService;
import epf.workflow.spi.TryService;
import epf.workflow.spi.WaitService;
import epf.workflow.task.schema.CallTask;
import epf.workflow.task.schema.DoTask;
import epf.workflow.task.schema.EmitTask;
import epf.workflow.task.schema.ForTask;
import epf.workflow.task.schema.ForkTask;
import epf.workflow.task.schema.ListenTask;
import epf.workflow.task.schema.RaiseTask;
import epf.workflow.task.schema.RunTask;
import epf.workflow.task.schema.SetTask;
import epf.workflow.task.schema.SwitchTask;
import epf.workflow.task.schema.TryTask;
import epf.workflow.task.schema.WaitTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaskServiceImpl implements TaskService {
	
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
	public Object start(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final URI taskURI, final Task task, final Object taskInput, final AtomicBoolean end) throws Error {
		final Instant taskStartedAt = Instant.now();
		fireTaskStartedEvent(workflow, arguments, taskURI, taskStartedAt);
		final TaskDescriptor taskDescriptor = createTaskDescriptor(workflowInput, taskName, taskURI, task, taskStartedAt);
		arguments.setTask(taskDescriptor);
		Object taskOutput = null;
		try {
			if(task instanceof CallTask) {
				final CallTask callTask = (CallTask) task;
				taskOutput = callService.call(workflow, workflowInput, arguments, taskName, callTask, taskInput);
			}
			else if(task instanceof DoTask) {
				final DoTask doTask = (DoTask) task;
				taskOutput = doService.do_(workflow, doTask.getDo_(), workflowInput, arguments, taskURI);
			}
			else if(task instanceof EmitTask) {
				final EmitTask emitTask = (EmitTask) task;
				taskOutput = emitService.emit(workflow, workflowInput, arguments, taskName, emitTask, taskInput);
			}
			else if(task instanceof ForkTask) {
				final ForkTask forkTask = (ForkTask) task;
				taskOutput = forkService.fork(workflow, workflowInput, arguments, taskName, forkTask, taskInput);
			}
			else if(task instanceof ForTask) {
				final ForTask forTask = (ForTask) task;
				taskOutput = forService._for(workflow, workflowInput, arguments, taskName, forTask, taskInput);
			}
			else if(task instanceof ListenTask) {
				final ListenTask listenTask = (ListenTask) task;
				taskOutput = listenService.listen(workflow, workflowInput, arguments, taskName, listenTask, taskInput);
			}
			else if(task instanceof RaiseTask) {
				final RaiseTask raiseTask = (RaiseTask) task;
				taskOutput = raiseService.raise(workflow, workflowInput, arguments, taskName, raiseTask, taskInput);
			}
			else if(task instanceof RunTask) {
				final RunTask runTask = (RunTask) task;
				taskOutput = runService.run(workflow, workflowInput, arguments, taskName, runTask, taskInput);
			}
			else if(task instanceof SetTask) {
				final SetTask setTask = (SetTask) task;
				taskOutput = setService.set(workflow, workflowInput, arguments, taskName, setTask, taskInput);
			}
			else if(task instanceof SwitchTask) {
				final SwitchTask switchTask = (SwitchTask) task;
				taskOutput = switchService._switch(workflow, workflowInput, arguments, taskName, switchTask, taskInput);
			}
			else if(task instanceof TryTask) {
				final TryTask tryTask = (TryTask) task;
				taskOutput = tryService._try(workflow, workflowInput, arguments, taskName, tryTask, taskInput);
			}
			else if(task instanceof WaitTask) {
				final WaitTask waitTask = (WaitTask) task;
				taskOutput = waitService.wait(workflow, workflowInput, arguments, taskName, waitTask, taskInput);
			}
		}
		catch(Error error) {
			fireTaskFaultedEvent(workflow, arguments, taskURI, error);
			throw error;
		}
		fireTaskCompletedEvent(workflow, arguments, taskURI);
		return taskOutput;
	}

	private void fireTaskCompletedEvent(final Workflow workflow, final RuntimeExpressionArguments arguments, final URI taskURI) throws RuntimeError {
		final Date completedAt = Date.from(Instant.now());
		final TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent();
		taskCompletedEvent.setTask(taskURI);
		taskCompletedEvent.setCompletedAt(completedAt);
		taskCompletedEvent.setWorkflow(WorkflowUtil.getName(workflow, arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskCompletedEvent);
	}

	private void fireTaskFaultedEvent(final Workflow workflow, final RuntimeExpressionArguments arguments, final URI taskURI, final Error error) throws RuntimeError {
		final Date faultedAt = Date.from(Instant.now());
		final TaskFaultedEvent taskFaultedEvent = new TaskFaultedEvent();
		taskFaultedEvent.setError(error);
		taskFaultedEvent.setFaultedAt(faultedAt);
		taskFaultedEvent.setTask(taskURI);
		taskFaultedEvent.setWorkflow(WorkflowUtil.getName(workflow, arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskFaultedEvent);
	}

	private TaskDescriptor createTaskDescriptor(final Object workflowInput, final String taskName, final URI taskURI, final Task task, final Instant taskStartedAt) {
		final DateTimeDescriptor dateTimeDescriptor = DateTimeDescriptor.from(taskStartedAt);
		final TaskDescriptor taskDescriptor = new TaskDescriptor();
		taskDescriptor.setDefinition(task);
		taskDescriptor.setInput(workflowInput);
		taskDescriptor.setName(taskName);
		taskDescriptor.setReference(taskURI.toString());
		taskDescriptor.setStartedAt(dateTimeDescriptor);
		return taskDescriptor;
	}

	private void fireTaskStartedEvent(final Workflow workflow, final RuntimeExpressionArguments arguments, final URI taskURI, final Instant taskStartedAt) throws RuntimeError {
		final TaskStartedEvent taskStartedEvent = new TaskStartedEvent();
		taskStartedEvent.setStartedAt(Date.from(taskStartedAt));
		taskStartedEvent.setTask(taskURI);
		taskStartedEvent.setWorkflow(WorkflowUtil.getName(workflow, arguments.getWorkflow()));
		taskLifecycleEventsService.fire(taskStartedEvent);
	}
}
