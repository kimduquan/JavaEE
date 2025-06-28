package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.DoTask;

public interface DoService {

	Object do_(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final DoTask task, final Object taskInput) throws Error;
}
