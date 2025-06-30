package epf.workflow.spi;

import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.TryTask;

public interface TryService {

	Object _try(final RuntimeExpressionArguments arguments, final TryTask task, final Object taskInput, final AtomicBoolean end) throws Error;
}
