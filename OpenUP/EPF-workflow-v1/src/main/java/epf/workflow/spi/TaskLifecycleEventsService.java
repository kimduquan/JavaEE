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

public interface TaskLifecycleEventsService {

	void fire(final TaskCreatedEvent event) throws Exception;
	void fire(final TaskStartedEvent event) throws Exception;
	void fire(final TaskSuspendedEvent event) throws Exception;
	void fire(final TaskResumedEvent event) throws Exception;
	void fire(final TaskRetriedEvent event) throws Exception;
	void fire(final TaskCancelledEvent event) throws Exception;
	void fire(final TaskFaultedEvent event) throws Exception;
	void fire(final TaskCompletedEvent event) throws Exception;
	void fire(final TaskStatusChangedEvent event) throws Exception;
}
