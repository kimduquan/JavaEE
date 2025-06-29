package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.CallTask;

public interface CallService {

	Object call(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final CallTask task, final Object taskInput) throws Error;
}
