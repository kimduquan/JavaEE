package epf.workflow.task.internal;

import epf.workflow.schema.Duration;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Wait;
import epf.workflow.task.WaitService;
import epf.workflow.util.DurationUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WaitServiceImpl implements WaitService {

	@Override
	public Object wait(final RuntimeExpressionArguments arguments, final Wait task, final Object taskInput) throws Exception {
		if(task.getWait().isLeft()) {
			final java.time.Duration duration = java.time.Duration.parse(task.getWait().getLeft());
			Thread.sleep(duration);
		}
		else if(task.getWait().isRight()) {
			final Duration duration = task.getWait().getRight();
			Thread.sleep(DurationUtil.getDuration(duration));
		}
		return taskInput;
	}
}
