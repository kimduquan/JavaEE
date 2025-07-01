package epf.workflow.spi;

import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.ForTask;

public interface ForService {

	Object _for(final RuntimeExpressionArguments arguments, final ForTask task, final Object taskInput, final AtomicReference<String> flowDirective) throws Error;
}
