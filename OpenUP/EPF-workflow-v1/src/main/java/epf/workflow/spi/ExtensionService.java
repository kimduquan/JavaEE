package epf.workflow.spi;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;

public interface ExtensionService {

	Object before(final RuntimeExpressionArguments arguments, final String taskName, final URI taskURI, final Task task, final Object taskInput, final AtomicReference<String> flowDirective) throws Error;
	Object after(final RuntimeExpressionArguments arguments, final String taskName, final URI taskURI, final Task task, final Object taskInput, final AtomicReference<String> flowDirective) throws Error;
}
