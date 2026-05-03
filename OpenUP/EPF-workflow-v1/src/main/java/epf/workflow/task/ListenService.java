package epf.workflow.task;

import epf.workflow.schema.Listen;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface ListenService {

	Object listen(final RuntimeExpressionArguments arguments, final Listen task, final Object taskInput) throws Exception;
}
