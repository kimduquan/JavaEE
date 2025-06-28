package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.EmitTask;

public interface EmitService {

	Object emit(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final EmitTask task, final Object taskInput) throws Error;
}
