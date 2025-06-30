package epf.workflow.spi;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;

public interface DoService {

	Object do_(final Map<String, Task> do_, final RuntimeExpressionArguments arguments, final URI parentURI, final AtomicBoolean end) throws Error;
}
