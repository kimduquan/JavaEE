package epf.workflow.task;

import epf.workflow.schema.Emit;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface EmitService {

	Object emit(final RuntimeExpressionArguments arguments, final Emit task, final Object taskInput) throws Exception;
}
