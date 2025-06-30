package epf.workflow.spi;

import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.ForkTask;

public interface ForkService {

	Object fork(final RuntimeExpressionArguments arguments, final ForkTask task, final AtomicBoolean end) throws Error;
}
