package epf.workflow.service;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.util.DurationUtil;
import epf.workflow.spi.WaitService;
import epf.workflow.task.schema.WaitTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WaitServiceImpl implements WaitService {

	@Override
	public Object wait(final RuntimeExpressionArguments arguments, final WaitTask task, final Object taskInput) throws Error {
		if(task.getWait().isLeft()) {
			final java.time.Duration duration = java.time.Duration.parse(task.getWait().getLeft());
			try {
				Thread.sleep(duration);
			}
			catch(Exception ex) {
				throw new RuntimeError(ex);
			}
		}
		else if(task.getWait().isRight()) {
			final Duration duration = task.getWait().getRight();
			try {
				Thread.sleep(DurationUtil.getDuration(duration));
			}
			catch(Exception ex) {
				throw new RuntimeError(ex);
			}
		}
		return taskInput;
	}
}
