package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.RunTask;

public interface RunService {

	Object run(final RuntimeExpressionArguments arguments, final RunTask task, final Object taskInput) throws Error;
}
