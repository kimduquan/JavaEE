package epf.workflow.event;

import epf.workflow.event.schema.WorkflowCancelledEvent;
import epf.workflow.event.schema.WorkflowCompletedEvent;
import epf.workflow.event.schema.WorkflowCorrelationCompletedEvent;
import epf.workflow.event.schema.WorkflowCorrelationStartedEvent;
import epf.workflow.event.schema.WorkflowFaultedEvent;
import epf.workflow.event.schema.WorkflowResumedEvent;
import epf.workflow.event.schema.WorkflowStartedEvent;
import epf.workflow.event.schema.WorkflowStatusChangedEvent;
import epf.workflow.event.schema.WorkflowSuspendedEvent;
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
