package epf.workflow.spi;

import epf.workflow.event.TaskCancelledEvent;
import epf.workflow.event.TaskCompletedEvent;
import epf.workflow.event.TaskCreatedEvent;
import epf.workflow.event.TaskFaultedEvent;
import epf.workflow.event.TaskResumedEvent;
import epf.workflow.event.TaskRetriedEvent;
import epf.workflow.event.TaskStartedEvent;
import epf.workflow.event.TaskStatusChangedEvent;
import epf.workflow.event.TaskSuspendedEvent;
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
