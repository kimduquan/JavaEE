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
	public void notify(final TaskCreatedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskStartedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskSuspendedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskResumedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskRetriedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskCancelledEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskFaultedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskCompletedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void notify(final TaskStatusChangedEvent event) throws Exception {
		emitter.send(event);
	}

}
