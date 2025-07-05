package epf.workflow.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.call.schema.CallTask;

public interface CallService {

	Object call(final RuntimeExpressionArguments arguments, final CallTask task, final Object taskInput) throws Error;
}
