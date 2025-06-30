package epf.workflow.service;

import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.TryService;
import epf.workflow.task.schema.TryTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TryServiceImpl implements TryService {

	@Override
	public Object _try(final RuntimeExpressionArguments arguments, final TryTask task, final Object taskInput, final AtomicBoolean end) throws Error {
		return null;
	}
}
