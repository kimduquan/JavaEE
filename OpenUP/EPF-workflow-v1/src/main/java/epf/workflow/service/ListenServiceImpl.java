package epf.workflow.service;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.ListenService;
import epf.workflow.task.schema.ListenTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListenServiceImpl implements ListenService {

	@Override
	public Object listen(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final ListenTask task, final Object taskInput) throws Error {
		return null;
	}

}
