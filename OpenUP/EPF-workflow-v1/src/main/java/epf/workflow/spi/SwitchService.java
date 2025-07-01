package epf.workflow.spi;

import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.SwitchTask;

public interface SwitchService {

	Object _switch(final RuntimeExpressionArguments arguments, final SwitchTask task, final Object taskInput, final AtomicReference<String> flowDirective) throws Error;
}
