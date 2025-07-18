package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.ListenTask;

public interface ListenService {

	Object listen(final RuntimeExpressionArguments arguments, final ListenTask task, final Object taskInput) throws Error;
}
