package epf.workflow.service;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.event.WorkflowCompletedEvent;
import epf.workflow.event.WorkflowFaultedEvent;
import epf.workflow.event.WorkflowStartedEvent;
import epf.workflow.schema.DateTimeDescriptor;
import epf.workflow.schema.RuntimeDescriptor;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.WorkflowDefinitionReference;
import epf.workflow.schema.WorkflowDescriptor;
import epf.workflow.schema.util.Either;
import epf.workflow.schema.util.WorkflowUtil;
import epf.workflow.spi.DoService;
import epf.workflow.spi.InputService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.spi.UseService;
import epf.workflow.spi.WorkflowLifecycleEventsService;
import epf.workflow.spi.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowServiceImpl implements WorkflowService {
	
	@Inject
	transient InputService inputService;
	
	@Inject
	transient UseService useService; 
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient WorkflowLifecycleEventsService workflowLifecycleEventsService;
	
	@Inject
	transient DoService doService;

	@Override
	public Workflow getWorkflow(final String name, final String version) throws RuntimeError {
		return null;
	}

	@Override
	public Object start(final Workflow workflow, Object workflowInput) throws Error {
		final Instant startedAt = Instant.now();
		final UUID uuid = UUID.randomUUID();
		final String workflowName = WorkflowUtil.getName(workflow.getDocument().getName(), uuid.toString(), workflow.getDocument().getNamespace());
		fireWorkflowStartedEvent(workflow, startedAt, workflowName);
		final RuntimeExpressionArguments arguments = createRuntimeExpressionArguments(workflow, startedAt, uuid);
		if(workflow.getInput() != null) {
			if(workflow.getInput().getSchema() != null) {
				inputService.validate(workflowInput, workflow.getInput());
			}
			if(!Either.isNull(workflow.getInput().getFrom())) {
				workflowInput = runtimeExpressionsService.evaluate(workflow.getInput().getFrom().getLeft(), arguments.getSecrets(), arguments.getWorkflow(), arguments.getRuntime());
			}
		}
		arguments.getWorkflow().setInput(workflowInput);
		try {
			final URI doURI = URI.create("/do");
			final AtomicBoolean end = new AtomicBoolean();
			final Object workflowOutput = doService.do_(workflow.getDo_(), arguments, doURI, end);
			fireWorkflowCompletedEvent(workflowName);
			return workflowOutput;
		}
		catch(Error error) {
			fireWorkflowFaultedEvent(workflowName, error);
			throw error;
		}
	}

	private RuntimeExpressionArguments createRuntimeExpressionArguments(final Workflow workflow, final Instant startedAt, final UUID uuid) throws Error {
		final DateTimeDescriptor dateTimeDescriptor = DateTimeDescriptor.from(startedAt);
		final WorkflowDescriptor workflowDescriptor = new WorkflowDescriptor();
		workflowDescriptor.setId(uuid.toString());
		workflowDescriptor.setDefinition(workflow);
		workflowDescriptor.setStartedAt(dateTimeDescriptor);
		final RuntimeDescriptor runtimeDescriptor = new RuntimeDescriptor();
		final Map<String, Object> context = new LinkedHashMap<>();
		final Map<String, Object> secrets = useService.useSecrets(workflow.getUse());
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setRuntime(runtimeDescriptor);
		arguments.setWorkflow(workflowDescriptor);
		return arguments;
	}

	private void fireWorkflowFaultedEvent(final String workflowName, Error error) throws RuntimeError {
		final Date faultedAt = Date.from(Instant.now());
		final WorkflowFaultedEvent workflowFaultedEvent = new WorkflowFaultedEvent();
		workflowFaultedEvent.setError(error);
		workflowFaultedEvent.setFaultedAt(faultedAt);
		workflowFaultedEvent.setName(workflowName);
		workflowLifecycleEventsService.fire(workflowFaultedEvent);
	}

	private void fireWorkflowCompletedEvent(final String workflowName) throws RuntimeError {
		final Date completedAt = Date.from(Instant.now());
		final WorkflowCompletedEvent workflowCompletedEvent = new WorkflowCompletedEvent();
		workflowCompletedEvent.setCompletedAt(completedAt);
		workflowCompletedEvent.setName(workflowName);
		workflowLifecycleEventsService.fire(workflowCompletedEvent);
	}

	private void fireWorkflowStartedEvent(final Workflow workflow, final Instant startedAt, final String workflowName) throws RuntimeError {
		final WorkflowDefinitionReference workflowDefinitionReference = new WorkflowDefinitionReference();
		workflowDefinitionReference.setName(workflow.getDocument().getName());
		workflowDefinitionReference.setNamespace(workflow.getDocument().getNamespace());
		workflowDefinitionReference.setVersion(workflow.getDocument().getVersion());
		final WorkflowStartedEvent workflowStartedEvent = new WorkflowStartedEvent();
		workflowStartedEvent.setDefinition(workflowDefinitionReference);
		workflowStartedEvent.setName(workflowName);
		workflowStartedEvent.setStartedAt(Date.from(startedAt));
		workflowLifecycleEventsService.fire(workflowStartedEvent);
	}
}
