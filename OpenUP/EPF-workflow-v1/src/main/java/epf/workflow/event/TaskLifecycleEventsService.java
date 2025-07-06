package epf.workflow.event;

import epf.workflow.event.schema.TaskCancelledEvent;
import epf.workflow.event.schema.TaskCompletedEvent;
import epf.workflow.event.schema.TaskCreatedEvent;
import epf.workflow.event.schema.TaskFaultedEvent;
import epf.workflow.event.schema.TaskResumedEvent;
import epf.workflow.event.schema.TaskRetriedEvent;
import epf.workflow.event.schema.TaskStartedEvent;
import epf.workflow.event.schema.TaskStatusChangedEvent;
import epf.workflow.event.schema.TaskSuspendedEvent;
import epf.workflow.schema.RuntimeError;

public interface TaskLifecycleEventsService {

	void fire(final TaskCreatedEvent event) throws RuntimeError;
	void fire(final TaskStartedEvent event) throws RuntimeError;
	void fire(final TaskSuspendedEvent event) throws RuntimeError;
	void fire(final TaskResumedEvent event) throws RuntimeError;
	void fire(final TaskRetriedEvent event) throws RuntimeError;
	void fire(final TaskCancelledEvent event) throws RuntimeError;
	void fire(final TaskFaultedEvent event) throws RuntimeError;
	void fire(final TaskCompletedEvent event) throws RuntimeError;
	void fire(final TaskStatusChangedEvent event) throws RuntimeError;
}
