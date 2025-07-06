package epf.workflow.internal;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.spi.ExtensionService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExtensionServiceImpl implements ExtensionService {

	@Override
	public Object before(RuntimeExpressionArguments arguments, String taskName, URI taskURI, Task task,
			Object taskInput, AtomicReference<String> flowDirective) throws Error {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object after(RuntimeExpressionArguments arguments, String taskName, URI taskURI, Task task, Object taskInput,
			AtomicReference<String> flowDirective) throws Error {
		// TODO Auto-generated method stub
		return null;
	}

}
