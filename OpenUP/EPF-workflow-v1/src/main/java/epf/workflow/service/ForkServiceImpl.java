package epf.workflow.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.util.DurationUtil;
import epf.workflow.spi.ForkService;
import epf.workflow.spi.TaskService;
import epf.workflow.spi.TimeoutService;
import epf.workflow.task.schema.ForkTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ForkServiceImpl implements ForkService {
	
	@Inject
	transient ManagedExecutor executor;
	
	@Inject
	transient TaskService taskService;
	
	@Inject
	transient TimeoutService timeoutService;

	@Override
	public Object fork(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final ForkTask task, final AtomicBoolean end) throws Error {
		final List<Callable<Object>> branchTasks = new ArrayList<>();
		task.getFork().getBranches().forEach((branchTaskName, branchTask) -> {
			final URI branchURI = URI.create(arguments.getTask().getReference()).resolve(branchTaskName);
			branchTasks.add(() -> taskService.start(workflow, workflowInput, arguments, branchTaskName, branchURI, branchTask, arguments.getInput(), end));
		});
		try {
			Object taskOutput;
			final Duration timeout = timeoutService.getTimeout(workflow, task);
			TimeUnit timeUnit = TimeUnit.NANOSECONDS;
			long time = 0;
			if(timeout != null) {
				timeUnit = DurationUtil.getTimeUnit(timeout);
				time = DurationUtil.getTime(timeout, timeUnit);
			}
			if(task.getFork().isCompete()) {
				final List<Object> outputs = new ArrayList<>();
				List<Future<Object>> futures;
				if(timeout != null) {
					futures = executor.invokeAll(branchTasks, time, timeUnit);
				}
				else {
					futures = executor.invokeAll(branchTasks);
				}
				for(Future<Object> future : futures) {
					outputs.add(future.get());
				}
				taskOutput = outputs;
			}
			else {
				if(timeout != null) {
					taskOutput = executor.invokeAny(branchTasks, time, timeUnit);
				}
				else {
					taskOutput = executor.invokeAny(branchTasks);
				}
			}
			return taskOutput;
		}
		catch(Exception ex) {
			throw new RuntimeError(ex);
		}
	}
}
