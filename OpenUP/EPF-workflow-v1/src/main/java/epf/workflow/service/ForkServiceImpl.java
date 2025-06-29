package epf.workflow.service;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Workflow;
import epf.workflow.spi.ForkService;
import epf.workflow.spi.TaskService;
import epf.workflow.task.schema.ForkTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ForkServiceImpl implements ForkService {
	
	@Inject
	transient ManagedExecutor executor;
	
	@Inject
	transient TaskService taskService;

	@Override
	public Object fork(final Workflow workflow, final Object workflowInput, final RuntimeExpressionArguments arguments, final ForkTask task, final AtomicBoolean end) throws Error {
		task.getFork().getBranches().forEach((branchTaskName, branchTask) -> {
			final URI branchURI = URI.create(arguments.getTask().getReference()).resolve(branchTaskName);
			executor.submit(() -> taskService.start(workflow, workflowInput, arguments, branchTaskName, branchURI, branchTask, arguments.getInput(), end));
		});
		return null;
	}

}
