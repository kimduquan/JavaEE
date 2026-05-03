package epf.workflow.event;

import epf.workflow.schema.WorkflowCancelledEvent;
import epf.workflow.schema.WorkflowCompletedEvent;
import epf.workflow.schema.WorkflowCorrelationCompletedEvent;
import epf.workflow.schema.WorkflowCorrelationStartedEvent;
import epf.workflow.schema.WorkflowFaultedEvent;
import epf.workflow.schema.WorkflowResumedEvent;
import epf.workflow.schema.WorkflowStartedEvent;
import epf.workflow.schema.WorkflowStatusChangedEvent;
import epf.workflow.schema.WorkflowSuspendedEvent;

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
