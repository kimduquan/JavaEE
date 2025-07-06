package epf.workflow.event.internal;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.workflow.event.TaskLifecycleEventsService;
import epf.workflow.event.schema.TaskCancelledEvent;
import epf.workflow.event.schema.TaskCompletedEvent;
import epf.workflow.event.schema.TaskCreatedEvent;
import epf.workflow.event.schema.TaskFaultedEvent;
import epf.workflow.event.schema.TaskLifecycleEvent;
import epf.workflow.event.schema.TaskResumedEvent;
import epf.workflow.event.schema.TaskRetriedEvent;
import epf.workflow.event.schema.TaskStartedEvent;
import epf.workflow.event.schema.TaskStatusChangedEvent;
import epf.workflow.event.schema.TaskSuspendedEvent;
import epf.workflow.schema.RuntimeError;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskLifecycleEventsServiceImpl implements TaskLifecycleEventsService {
	
	@Channel(Naming.Workflow.TASK_LIFECYCLE_EVENTS)
	transient Emitter<TaskLifecycleEvent> emitter;

	@Override
	public void fire(final TaskCreatedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskStartedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskSuspendedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskResumedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskRetriedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskCancelledEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskFaultedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskCompletedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final TaskStatusChangedEvent event) throws RuntimeError {
		emitter.send(event);
	}

}
