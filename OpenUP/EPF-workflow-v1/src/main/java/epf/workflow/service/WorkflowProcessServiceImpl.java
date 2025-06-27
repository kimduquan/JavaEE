package epf.workflow.service;

import java.util.Date;
import java.util.UUID;

import epf.workflow.schema.Naming;
import epf.workflow.schema.WorkflowDefinitionReference;
import epf.workflow.schema.WorkflowProcess;
import epf.workflow.spi.WorkflowProcessService;
import epf.workflow.spi.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkflowProcessServiceImpl implements WorkflowProcessService {
	
	@Inject
	transient WorkflowService workflowService;

	@Override
	public void run(final WorkflowProcess workflowProcess) throws Exception {
		final WorkflowDefinitionReference workflowDefinitionReference = new WorkflowDefinitionReference();
		workflowDefinitionReference.setName(workflowProcess.getName());
		workflowDefinitionReference.setVersion(workflowProcess.getVersion());
		final String name = Naming.getName(workflowDefinitionReference.getName(), UUID.randomUUID(), workflowDefinitionReference.getNamespace(), workflowProcess.getName());
		workflowService.start(null, workflowDefinitionReference, new Date());
	}
}
