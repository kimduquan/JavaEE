package epf.workflow.event.internal;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.workflow.event.WorkflowLifecycleEventsService;
import epf.workflow.schema.WorkflowCancelledEvent;
import epf.workflow.schema.WorkflowCompletedEvent;
import epf.workflow.schema.WorkflowCorrelationCompletedEvent;
import epf.workflow.schema.WorkflowCorrelationStartedEvent;
import epf.workflow.schema.WorkflowFaultedEvent;
import epf.workflow.schema.WorkflowResumedEvent;
import epf.workflow.schema.WorkflowStartedEvent;
import epf.workflow.schema.WorkflowStatusChangedEvent;
import epf.workflow.schema.WorkflowSuspendedEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkflowLifecycleEventsServiceImpl implements WorkflowLifecycleEventsService {
	
	@Channel(Naming.Workflow.WORKFLOW_LIFECYCLE_EVENTS)
	transient Emitter<Object> emitter;

	@Override
	public void fire(final WorkflowStartedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowSuspendedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowResumedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCorrelationStartedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCorrelationCompletedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCancelledEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowFaultedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCompletedEvent event) throws Exception {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowStatusChangedEvent event) throws Exception {
		emitter.send(event);
	}

}
