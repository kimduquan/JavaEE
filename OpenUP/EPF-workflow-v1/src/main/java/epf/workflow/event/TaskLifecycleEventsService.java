package epf.workflow.event;

import epf.workflow.schema.TaskCancelledEvent;
import epf.workflow.schema.TaskCompletedEvent;
import epf.workflow.schema.TaskCreatedEvent;
import epf.workflow.schema.TaskFaultedEvent;
import epf.workflow.schema.TaskResumedEvent;
import epf.workflow.schema.TaskRetriedEvent;
import epf.workflow.schema.TaskStartedEvent;
import epf.workflow.schema.TaskStatusChangedEvent;
import epf.workflow.schema.TaskSuspendedEvent;

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
