package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.TryTask;

public interface TryService {

	Object _try(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final TryTask task, final Object taskInput) throws Error;
}
