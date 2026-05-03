package epf.workflow.task;

import epf.workflow.schema.Call;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface CallService {

	Object call(final RuntimeExpressionArguments arguments, final Call<?> task, final Object taskInput) throws Exception;
}
