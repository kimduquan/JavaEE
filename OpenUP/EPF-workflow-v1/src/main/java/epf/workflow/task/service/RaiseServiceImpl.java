package epf.workflow.task.service;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.schema.RaiseTask;
import epf.workflow.task.spi.RaiseService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RaiseServiceImpl implements RaiseService {

	@Override
	public Object raise(final RuntimeExpressionArguments arguments, final RaiseTask task, final Object taskInput) throws Error {
		Error error = null;
		if(task.getRaise().getError().isLeft()) {
			error = arguments.getWorkflow().getDefinition().getUse().getErrors().get(task.getRaise().getError().getLeft());
		}
		else {
			error = task.getRaise().getError().getRight();
		}
		final Error errorInstance = error.clone();
		errorInstance.setInstance(arguments.getTask().getReference());
		throw errorInstance;
	}
}
