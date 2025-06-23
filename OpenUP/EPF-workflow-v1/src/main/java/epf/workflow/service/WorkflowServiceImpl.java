package epf.workflow.service;

import java.util.Date;
import java.util.Map;
import epf.workflow.event.WorkflowCancelledEvent;
import epf.workflow.event.WorkflowCompletedEvent;
import epf.workflow.event.WorkflowCorrelationCompletedEvent;
import epf.workflow.event.WorkflowCorrelationStartedEvent;
import epf.workflow.event.WorkflowFaultedEvent;
import epf.workflow.event.WorkflowResumedEvent;
import epf.workflow.event.WorkflowStartedEvent;
import epf.workflow.event.WorkflowStatusChangedEvent;
import epf.workflow.event.WorkflowSuspendedEvent;
import epf.workflow.schema.Error;
import epf.workflow.schema.WorkflowDefinitionReference;
import epf.workflow.spi.WorkflowLifecycleEventsService;
import epf.workflow.spi.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowServiceImpl implements WorkflowService {
	
	@Inject
	transient WorkflowLifecycleEventsService events;

	@Override
	public void start(final String name, final WorkflowDefinitionReference definition, final Date startedAt) throws Exception {
		final WorkflowStartedEvent event = new WorkflowStartedEvent();
		event.setName(name);
		event.setStartedAt(startedAt);
		event.setDefinition(definition);
		events.notify(event);
	}

	@Override
	public void suspend(final String name, final Date suspendedAt) throws Exception {
		final WorkflowSuspendedEvent event = new WorkflowSuspendedEvent();
		event.setName(name);
		event.setSuspendedAt(suspendedAt);
		events.notify(event);
	}

	@Override
	public void resume(final String name, final Date resumedAt) throws Exception {
		final WorkflowResumedEvent event = new WorkflowResumedEvent();
		event.setName(name);
		event.setResumedAt(resumedAt);
		events.notify(event);
	}

	@Override
	public void startCorrelation(final String name, final Date startedAt) throws Exception {
		final WorkflowCorrelationStartedEvent event = new WorkflowCorrelationStartedEvent();
		event.setName(name);
		event.setStartedAt(startedAt);
		events.notify(event);
	}

	@Override
	public void completeCorrelation(final String name, final Date completedAt) throws Exception {
		completeCorrelation(name, completedAt, null);
	}

	@Override
	public void completeCorrelation(final String name, final Date completedAt, final Map<String, String> correlationKeys)
			throws Exception {
		final WorkflowCorrelationCompletedEvent event = new WorkflowCorrelationCompletedEvent();
		event.setName(name);
		event.setCompletedAt(completedAt);
		event.setCorrelationKeys(correlationKeys);
		events.notify(event);
	}

	@Override
	public void cancel(final String name, final Date cancelledAt) throws Exception {
		final WorkflowCancelledEvent event = new WorkflowCancelledEvent();
		event.setName(name);
		event.setCancelledAt(cancelledAt);
		events.notify(event);
	}

	@Override
	public void fault(final String name, final Date faultedAt, final Error error) throws Exception {
		final WorkflowFaultedEvent event = new WorkflowFaultedEvent();
		event.setName(name);
		event.setFaultedAt(faultedAt);
		event.setError(error);
		events.notify(event);
	}

	@Override
	public void complete(final String name, final Date completedAt) throws Exception {
		complete(name, completedAt, null);
	}

	@Override
	public void complete(final String name, final Date completedAt, final Map<String, String> output) throws Exception {
		final WorkflowCompletedEvent event = new WorkflowCompletedEvent();
		event.setName(name);
		event.setCompletedAt(completedAt);
		event.setOutput(output);
		events.notify(event);
	}

	@Override
	public void changeStatus(final String name, final Date updatedAt, final String status) throws Exception {
		final WorkflowStatusChangedEvent event = new WorkflowStatusChangedEvent();
		event.setName(name);
		event.setUpdatedAt(updatedAt);
		event.setStatus(status);
		events.notify(event);
	}

}
