package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.SetTask;

public interface SetService {

	Object set(final RuntimeExpressionArguments arguments, final SetTask task, final Object taskInput) throws Error;
}
