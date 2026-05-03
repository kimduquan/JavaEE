package epf.workflow.event.internal;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.workflow.event.TaskLifecycleEventsService;
import epf.workflow.schema.TaskCancelledEvent;
import epf.workflow.schema.TaskCompletedEvent;
import epf.workflow.schema.TaskCreatedEvent;
import epf.workflow.schema.TaskFaultedEvent;
import epf.workflow.schema.TaskResumedEvent;
import epf.workflow.schema.TaskRetriedEvent;
import epf.workflow.schema.TaskStartedEvent;
import epf.workflow.schema.TaskStatusChangedEvent;
import epf.workflow.schema.TaskSuspendedEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskLifecycleEventsServiceImpl implements TaskLifecycleEventsService {
	
	@Channel(Naming.Workflow.TASK_LIFECYCLE_EVENTS)
	transient Emitter<Object> emitter;

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
