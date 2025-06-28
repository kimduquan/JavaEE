package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.ForkTask;

public interface ForkService {

	Object fork(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final ForkTask task, final Object taskInput) throws Error;
}
