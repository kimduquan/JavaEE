package epf.workflow.task.internal;

import epf.workflow.schema.Error;
import epf.workflow.schema.Raise;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.RaiseService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RaiseServiceImpl implements RaiseService {

	@Override
	public Object raise(final RuntimeExpressionArguments arguments, final Raise task, final Object taskInput) throws Exception {
		Error error = null;
		if(task.getRaise().getError().isLeft()) {
			error = arguments.getWorkflow().getDefinition().getUse().getErrors().get(task.getRaise().getError().getLeft());
		}
		else {
			error = task.getRaise().getError().getRight();
		}
		final Error errorInstance = new Error();
		errorInstance.setDetail(error.getDetail());
		errorInstance.setStatus(error.getStatus());
		errorInstance.setTitle(error.getTitle());
		errorInstance.setType(error.getType());
		errorInstance.setInstance(arguments.getTask().getReference());
		return null;
	}
}
