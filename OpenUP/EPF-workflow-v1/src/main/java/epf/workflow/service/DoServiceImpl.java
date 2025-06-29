package epf.workflow.service;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.DoService;
import epf.workflow.task.schema.DoTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoServiceImpl implements DoService {

	@Override
	public Object do_(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final DoTask task, final Object taskInput) throws Error {
		
		return null;
	}
}
