package epf.workflow.task;

import epf.workflow.schema.Run;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface RunService {

	Object run(final RuntimeExpressionArguments arguments, final Run task, final Object taskInput) throws Exception;
}
