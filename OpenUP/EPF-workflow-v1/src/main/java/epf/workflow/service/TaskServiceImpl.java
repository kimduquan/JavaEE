package epf.workflow.service;

import java.util.Date;
import java.util.Map;
import epf.workflow.event.TaskCancelledEvent;
import epf.workflow.event.TaskCompletedEvent;
import epf.workflow.event.TaskCreatedEvent;
import epf.workflow.event.TaskFaultedEvent;
import epf.workflow.event.TaskResumedEvent;
import epf.workflow.event.TaskRetriedEvent;
import epf.workflow.event.TaskStartedEvent;
import epf.workflow.event.TaskStatusChangedEvent;
import epf.workflow.event.TaskSuspendedEvent;
import epf.workflow.schema.Error;
import epf.workflow.spi.TaskLifecycleEventsService;
import epf.workflow.spi.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaskServiceImpl implements TaskService {
	
	@Inject
	transient TaskLifecycleEventsService events;

	@Override
	public void create(final String workflow, final String task, final Date createdAt) throws Exception {
		final TaskCreatedEvent event = new TaskCreatedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setCreatedAt(createdAt);
		events.notify(event);
	}

	@Override
	public void start(final String workflow, final String task, final Date startedAt) throws Exception {
		final TaskStartedEvent event = new TaskStartedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setStartedAt(startedAt);
		events.notify(event);
	}

	@Override
	public void suspend(final String workflow, final String task, final Date suspendedAt) throws Exception {
		final TaskSuspendedEvent event = new TaskSuspendedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setSuspendedAt(suspendedAt);
		events.notify(event);
	}

	@Override
	public void resume(final String workflow, final String task, final Date resumedAt) throws Exception {
		final TaskResumedEvent event = new TaskResumedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setResumedAt(resumedAt);
		events.notify(event);
	}

	@Override
	public void retry(final String workflow, final String task, final Date retriedAt) throws Exception {
		final TaskRetriedEvent event = new TaskRetriedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setRetriedAt(retriedAt);
		events.notify(event);
	}

	@Override
	public void cancel(final String workflow, final String task, final Date cancelledAt) throws Exception {
		final TaskCancelledEvent event = new TaskCancelledEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setCancelledAt(cancelledAt);
		events.notify(event);
	}

	@Override
	public void fault(final String workflow, final String task, final Date faultedAt, final Error error) throws Exception {
		final TaskFaultedEvent event = new TaskFaultedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setFaultedAt(faultedAt);
		event.setError(error);
		events.notify(event);
	}

	@Override
	public void complete(final String workflow, final String task, final Date completedAt) throws Exception {
		complete(workflow, task, completedAt, null);
	}

	@Override
	public void complete(final String workflow, final String task, final Date completedAt, final Map<String, String> output) throws Exception {
		final TaskCompletedEvent event = new TaskCompletedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setCompletedAt(completedAt);
		event.setOutput(output);
		events.notify(event);
	}

	@Override
	public void changeStatus(final String workflow, final String task, final Date updatedAt, final String status) throws Exception {
		final TaskStatusChangedEvent event = new TaskStatusChangedEvent();
		event.setWorkflow(workflow);
		event.setTask(task);
		event.setUpdatedAt(updatedAt);
		event.setStatus(status);
		events.notify(event);
	}

}
