package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.RaiseTask;

public interface RaiseService {

	Object raise(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final RaiseTask task, final Object taskInput) throws Error;
}
