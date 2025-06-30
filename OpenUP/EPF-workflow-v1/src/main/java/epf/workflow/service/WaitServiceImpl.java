package epf.workflow.service;

import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.WaitService;
import epf.workflow.task.schema.WaitTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WaitServiceImpl implements WaitService {

	@Override
	public Object wait(final RuntimeExpressionArguments arguments, final WaitTask task, final Object taskInput) throws Error {
		return null;
	}
}
