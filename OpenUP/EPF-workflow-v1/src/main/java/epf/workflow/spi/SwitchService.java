package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.task.schema.SwitchTask;

public interface SwitchService {

	Object _switch(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final String taskName, final SwitchTask task, final Object taskInput) throws Error;
}
