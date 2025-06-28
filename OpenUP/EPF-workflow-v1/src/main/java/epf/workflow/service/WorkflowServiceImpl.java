package epf.workflow.service;

import java.util.Date;
import java.util.UUID;
import epf.workflow.event.WorkflowStartedEvent;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.WorkflowDefinitionReference;
import epf.workflow.schema.util.WorkflowUtil;
import epf.workflow.spi.WorkflowLifecycleEventsService;
import epf.workflow.spi.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowServiceImpl implements WorkflowService {
	
	@Inject
	transient WorkflowLifecycleEventsService workflowLifecycleEventsService;

	@Override
	public Workflow getWorkflow(final String name, final String version) throws Exception {
		return null;
	}

	@Override
	public void start(final Workflow workflow) throws Exception {
		final WorkflowDefinitionReference workflowDefinitionReference = new WorkflowDefinitionReference();
		workflowDefinitionReference.setName(workflow.getDocument().getName());
		workflowDefinitionReference.setNamespace(workflow.getDocument().getNamespace());
		workflowDefinitionReference.setVersion(workflow.getDocument().getVersion());
		final WorkflowStartedEvent workflowStartedEvent = new WorkflowStartedEvent();
		workflowStartedEvent.setDefinition(workflowDefinitionReference);
		workflowStartedEvent.setName(WorkflowUtil.getName(workflow.getDocument().getName(), UUID.randomUUID(), workflow.getDocument().getNamespace()));
		workflowStartedEvent.setStartedAt(new Date());
		workflowLifecycleEventsService.fire(workflowStartedEvent);
	}

}
