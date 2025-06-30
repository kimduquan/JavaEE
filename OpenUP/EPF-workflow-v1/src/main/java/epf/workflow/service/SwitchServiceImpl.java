package epf.workflow.service;

import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.SwitchService;
import epf.workflow.task.schema.SwitchTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SwitchServiceImpl implements SwitchService {

	@Override
	public Object _switch(final RuntimeExpressionArguments arguments, final SwitchTask task, final Object taskInput, final AtomicBoolean end) throws Error {
		return null;
	}

}
