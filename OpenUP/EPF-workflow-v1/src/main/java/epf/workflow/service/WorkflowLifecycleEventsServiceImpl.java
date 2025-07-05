package epf.workflow.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.workflow.event.schema.WorkflowCancelledEvent;
import epf.workflow.event.schema.WorkflowCompletedEvent;
import epf.workflow.event.schema.WorkflowCorrelationCompletedEvent;
import epf.workflow.event.schema.WorkflowCorrelationStartedEvent;
import epf.workflow.event.schema.WorkflowFaultedEvent;
import epf.workflow.event.schema.WorkflowLifecycleEvent;
import epf.workflow.event.schema.WorkflowResumedEvent;
import epf.workflow.event.schema.WorkflowStartedEvent;
import epf.workflow.event.schema.WorkflowStatusChangedEvent;
import epf.workflow.event.schema.WorkflowSuspendedEvent;
import epf.workflow.event.spi.WorkflowLifecycleEventsService;
import epf.workflow.schema.RuntimeError;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkflowLifecycleEventsServiceImpl implements WorkflowLifecycleEventsService {
	
	@Channel(Naming.Workflow.WORKFLOW_LIFECYCLE_EVENTS)
	transient Emitter<WorkflowLifecycleEvent> emitter;

	@Override
	public void fire(final WorkflowStartedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowSuspendedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowResumedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCorrelationStartedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCorrelationCompletedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCancelledEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowFaultedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowCompletedEvent event) throws RuntimeError {
		emitter.send(event);
	}

	@Override
	public void fire(final WorkflowStatusChangedEvent event) throws RuntimeError {
		emitter.send(event);
	}

}
