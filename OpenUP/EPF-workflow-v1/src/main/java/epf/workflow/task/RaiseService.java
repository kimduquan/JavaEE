package epf.workflow.task;

import epf.workflow.schema.Raise;
import epf.workflow.schema.RuntimeExpressionArguments;

public interface RaiseService {

	Object raise(final RuntimeExpressionArguments arguments, final Raise task, final Object taskInput) throws Exception;
}
