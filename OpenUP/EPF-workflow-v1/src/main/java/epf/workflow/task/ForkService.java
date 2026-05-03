package epf.workflow.task;

import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Fork;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface ForkService {

	Object fork(final RuntimeExpressionArguments arguments, final Fork task, final AtomicReference<String> flowDirective) throws Exception;
}
