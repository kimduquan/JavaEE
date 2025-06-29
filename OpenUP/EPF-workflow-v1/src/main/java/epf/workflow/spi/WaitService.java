package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.WaitTask;

public interface WaitService {

	Object wait(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final WaitTask task, final Object taskInput) throws Error;
}
