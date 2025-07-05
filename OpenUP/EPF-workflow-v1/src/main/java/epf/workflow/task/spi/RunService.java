package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.run.schema.RunTask;

public interface RunService {

	Object run(final RuntimeExpressionArguments arguments, final RunTask task, final Object taskInput) throws Error;
}
