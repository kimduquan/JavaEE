package epf.workflow.task;

import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Set;

public interface SetService {

	Object set(final RuntimeExpressionArguments arguments, final Set task, final Object taskInput) throws Exception;
}
