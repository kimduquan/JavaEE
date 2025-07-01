package epf.workflow.service;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.SetService;
import epf.workflow.task.schema.SetTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SetServiceImpl implements SetService {

	@Override
	public Object set(final RuntimeExpressionArguments arguments, final SetTask task, final Object taskInput) throws Error {
		if(task.getSet().isRight()) {
			arguments.getContext().putAll(task.getSet().getRight());
		}
		return null;
	}
}
