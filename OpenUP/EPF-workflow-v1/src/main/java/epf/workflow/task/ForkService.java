package epf.workflow.task;

import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.ForkTask;

public interface ForkService {

	Object fork(final RuntimeExpressionArguments arguments, final ForkTask task, final AtomicReference<String> flowDirective) throws Error;
}
