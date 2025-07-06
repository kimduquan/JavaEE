package epf.workflow.task;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.EmitTask;

public interface EmitService {

	Object emit(final RuntimeExpressionArguments arguments, final EmitTask task, final Object taskInput) throws Error;
}
