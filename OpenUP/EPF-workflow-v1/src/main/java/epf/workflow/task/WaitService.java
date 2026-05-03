package epf.workflow.task;

import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Wait;

public interface WaitService {

	Object wait(final RuntimeExpressionArguments arguments, final Wait task, final Object taskInput) throws Exception;
}
