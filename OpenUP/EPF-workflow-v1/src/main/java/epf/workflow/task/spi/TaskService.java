package epf.workflow.task.spi;

import epf.workflow.schema.Task;
import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface TaskService {

	Object start(final RuntimeExpressionArguments arguments, final String taskName, final URI taskURI, final Task task, final Object taskInput, final AtomicReference<String> flowDirective) throws Error;
}
