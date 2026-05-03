package epf.workflow.task;

import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Switch;

public interface SwitchService {

	Object _switch(final RuntimeExpressionArguments arguments, final Switch task, final Object taskInput, final AtomicReference<String> flowDirective) throws Exception;
}
