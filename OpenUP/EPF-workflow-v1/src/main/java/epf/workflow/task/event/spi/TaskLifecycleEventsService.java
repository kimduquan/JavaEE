package epf.workflow.task.event.spi;

import epf.workflow.schema.RuntimeError;
import epf.workflow.task.event.schema.TaskCancelledEvent;
import epf.workflow.task.event.schema.TaskCompletedEvent;
import epf.workflow.task.event.schema.TaskCreatedEvent;
import epf.workflow.task.event.schema.TaskFaultedEvent;
import epf.workflow.task.event.schema.TaskResumedEvent;
import epf.workflow.task.event.schema.TaskRetriedEvent;
import epf.workflow.task.event.schema.TaskStartedEvent;
import epf.workflow.task.event.schema.TaskStatusChangedEvent;
import epf.workflow.task.event.schema.TaskSuspendedEvent;

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
