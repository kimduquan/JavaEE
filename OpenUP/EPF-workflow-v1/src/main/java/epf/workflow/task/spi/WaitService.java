package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.WaitTask;

public interface WaitService {

	Object wait(final RuntimeExpressionArguments arguments, final WaitTask task, final Object taskInput) throws Error;
}
