package epf.workflow.spi;

import epf.workflow.event.WorkflowCancelledEvent;
import epf.workflow.event.WorkflowCompletedEvent;
import epf.workflow.event.WorkflowCorrelationCompletedEvent;
import epf.workflow.event.WorkflowCorrelationStartedEvent;
import epf.workflow.event.WorkflowFaultedEvent;
import epf.workflow.event.WorkflowResumedEvent;
import epf.workflow.event.WorkflowStartedEvent;
import epf.workflow.event.WorkflowStatusChangedEvent;
import epf.workflow.event.WorkflowSuspendedEvent;

public interface WorkflowLifecycleEventsService {

	void fire(final WorkflowStartedEvent event) throws Exception;
	void fire(final WorkflowSuspendedEvent event) throws Exception;
	void fire(final WorkflowResumedEvent event) throws Exception;
	void fire(final WorkflowCorrelationStartedEvent event) throws Exception;
	void fire(final WorkflowCorrelationCompletedEvent event) throws Exception;
	void fire(final WorkflowCancelledEvent event) throws Exception;
	void fire(final WorkflowFaultedEvent event) throws Exception;
	void fire(final WorkflowCompletedEvent event) throws Exception;
	void fire(final WorkflowStatusChangedEvent event) throws Exception;
}
