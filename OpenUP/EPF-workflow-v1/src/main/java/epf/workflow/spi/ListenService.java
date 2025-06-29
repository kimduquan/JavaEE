package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.ListenTask;

public interface ListenService {

	Object listen(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final ListenTask task, final Object taskInput) throws Error;
}
