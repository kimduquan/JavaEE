package epf.workflow.service;

import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.ForService;
import epf.workflow.task.schema.ForTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ForServiceImpl implements ForService {

	@Override
	public Object _for(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final ForTask task, final Object taskInput, final AtomicBoolean end) throws Error {
		return null;
	}

}
