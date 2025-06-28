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
import epf.workflow.schema.RuntimeError;

public interface WorkflowLifecycleEventsService {

	void fire(final WorkflowStartedEvent event) throws RuntimeError;
	void fire(final WorkflowSuspendedEvent event) throws RuntimeError;
	void fire(final WorkflowResumedEvent event) throws RuntimeError;
	void fire(final WorkflowCorrelationStartedEvent event) throws RuntimeError;
	void fire(final WorkflowCorrelationCompletedEvent event) throws RuntimeError;
	void fire(final WorkflowCancelledEvent event) throws RuntimeError;
	void fire(final WorkflowFaultedEvent event) throws RuntimeError;
	void fire(final WorkflowCompletedEvent event) throws RuntimeError;
	void fire(final WorkflowStatusChangedEvent event) throws RuntimeError;
}
