package epf.workflow.task;

import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.For;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface ForService {

	Object _for(final RuntimeExpressionArguments arguments, final For task, final Object taskInput, final AtomicReference<String> flowDirective) throws Exception;
}
