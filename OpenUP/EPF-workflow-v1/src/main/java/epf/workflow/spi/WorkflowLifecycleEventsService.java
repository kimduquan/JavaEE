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

	void notify(final WorkflowStartedEvent event) throws Exception;
	void notify(final WorkflowSuspendedEvent event) throws Exception;
	void notify(final WorkflowResumedEvent event) throws Exception;
	void notify(final WorkflowCorrelationStartedEvent event) throws Exception;
	void notify(final WorkflowCorrelationCompletedEvent event) throws Exception;
	void notify(final WorkflowCancelledEvent event) throws Exception;
	void notify(final WorkflowFaultedEvent event) throws Exception;
	void notify(final WorkflowCompletedEvent event) throws Exception;
	void notify(final WorkflowStatusChangedEvent event) throws Exception;
}
