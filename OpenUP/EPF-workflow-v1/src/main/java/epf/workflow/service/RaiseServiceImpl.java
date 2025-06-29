package epf.workflow.service;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.RaiseService;
import epf.workflow.task.schema.RaiseTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RaiseServiceImpl implements RaiseService {

	@Override
	public Object raise(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final RaiseTask task, final Object taskInput) throws Error {
		return null;
	}
}
