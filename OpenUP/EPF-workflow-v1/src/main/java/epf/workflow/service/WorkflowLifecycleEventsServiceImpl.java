package epf.workflow.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.workflow.event.WorkflowCancelledEvent;
import epf.workflow.event.WorkflowCompletedEvent;
import epf.workflow.event.WorkflowCorrelationCompletedEvent;
import epf.workflow.event.WorkflowCorrelationStartedEvent;
import epf.workflow.event.WorkflowFaultedEvent;
import epf.workflow.event.WorkflowLifecycleEvent;
import epf.workflow.event.WorkflowResumedEvent;
import epf.workflow.event.WorkflowStartedEvent;
import epf.workflow.event.WorkflowStatusChangedEvent;
import epf.workflow.event.WorkflowSuspendedEvent;
import epf.workflow.spi.WorkflowLifecycleEventsService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkflowLifecycleEventsServiceImpl implements WorkflowLifecycleEventsService {
	
	@Channel(Naming.Workflow.WORKFLOW_LIFECYCLE_EVENTS)
	transient Emitter<WorkflowLifecycleEvent> emitter;

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
