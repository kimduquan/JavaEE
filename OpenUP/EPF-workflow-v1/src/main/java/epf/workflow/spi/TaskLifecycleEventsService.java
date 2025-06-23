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

	void notify(final TaskCreatedEvent event) throws Exception;
	void notify(final TaskStartedEvent event) throws Exception;
	void notify(final TaskSuspendedEvent event) throws Exception;
	void notify(final TaskResumedEvent event) throws Exception;
	void notify(final TaskRetriedEvent event) throws Exception;
	void notify(final TaskCancelledEvent event) throws Exception;
	void notify(final TaskFaultedEvent event) throws Exception;
	void notify(final TaskCompletedEvent event) throws Exception;
	void notify(final TaskStatusChangedEvent event) throws Exception;
}
