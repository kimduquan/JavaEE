package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.ForTask;

public interface ForService {

	Object _for(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final ForTask task, final Object taskInput) throws Error;
}
