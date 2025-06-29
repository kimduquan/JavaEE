package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.SetTask;

public interface SetService {

	Object set(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final SetTask task, final Object taskInput) throws Error;
}
