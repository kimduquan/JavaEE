package epf.workflow.task.spi;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.RaiseTask;

public interface RaiseService {

	Object raise(final RuntimeExpressionArguments arguments, final RaiseTask task, final Object taskInput) throws Error;
}
