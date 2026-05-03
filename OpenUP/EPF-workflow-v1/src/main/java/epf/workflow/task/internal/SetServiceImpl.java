package epf.workflow.task.internal;

import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Set;
import epf.workflow.task.SetService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SetServiceImpl implements SetService {

	@Override
	public Object set(final RuntimeExpressionArguments arguments, final Set task, final Object taskInput) throws Exception {
		if(task.getSet().isLeft()) {
			arguments.getContext().putAll(task.getSet().getLeft());
		}
		return taskInput;
	}
}
