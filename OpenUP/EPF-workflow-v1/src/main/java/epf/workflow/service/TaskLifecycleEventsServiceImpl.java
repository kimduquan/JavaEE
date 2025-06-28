package epf.workflow.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.workflow.event.TaskCancelledEvent;
import epf.workflow.event.TaskCompletedEvent;
import epf.workflow.event.TaskCreatedEvent;
import epf.workflow.event.TaskFaultedEvent;
import epf.workflow.event.TaskLifecycleEvent;
import epf.workflow.event.TaskResumedEvent;
import epf.workflow.event.TaskRetriedEvent;
import epf.workflow.event.TaskStartedEvent;
import epf.workflow.event.TaskStatusChangedEvent;
import epf.workflow.event.TaskSuspendedEvent;
import epf.workflow.spi.TaskLifecycleEventsService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskLifecycleEventsServiceImpl implements TaskLifecycleEventsService {
	
	@Channel(Naming.Workflow.TASK_LIFECYCLE_EVENTS)
	transient Emitter<TaskLifecycleEvent> emitter;

	@Override
	public void fire(final TaskCreatedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskStartedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskSuspendedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskResumedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskRetriedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskCancelledEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskFaultedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskCompletedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskStatusChangedEvent event) throws Exception {
		emitter.send(event);
	}

}
