package epf.workflow.task.internal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.net.URI;
import epf.workflow.schema.Error;
import epf.workflow.schema.FlowDirective;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Task;
import epf.workflow.spi.ExtensionService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.task.DoService;
import epf.workflow.task.ForService;
import epf.workflow.task.TaskService;
import epf.workflow.task.schema.ForTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ForServiceImpl implements ForService {
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient DoService doService;
	
	@Inject
	transient TaskService taskService;
	
	@Inject
	transient ExtensionService extensionService;

	@Override
	public Object _for(final RuntimeExpressionArguments arguments, final ForTask task, final Object taskInput, final AtomicReference<String> flowDirective) throws Error {
		Object output = null;
		final Map<String, Object> parentContext = arguments.getContext();
		final URI taskURI = URI.create(arguments.getTask().getReference());
		final List<Object> in = runtimeExpressionsService.in(task.getFor_().getIn(), parentContext, arguments.getSecrets());
		Integer at = 0;
		while(runtimeExpressionsService.if_(task.getWhile_(), parentContext, arguments.getSecrets())) {
			final Object each = in.get(at);
			final Map<String, Object> forContext = runtimeExpressionsService.createContext(parentContext);
			runtimeExpressionsService.set(forContext, task.getFor_().getAt(), at);
			runtimeExpressionsService.set(forContext, task.getFor_().getEach(), each);
			arguments.setContext(forContext);
			output = doService.do_(task.getDo_(), arguments, taskURI, flowDirective);
			at++;
			if(FlowDirective._continue.equals(flowDirective.get())) {
				continue;
			}
			else if(FlowDirective.exit.equals(flowDirective.get()) || FlowDirective.end.equals(flowDirective.get())) {
				break;
			}
			else if(flowDirective.get() != null) {
				final String nextTaskName = flowDirective.get();
				final Task nextTask = arguments.getWorkflow().getDefinition().getUse().getFunctions().get(nextTaskName);
				final URI nextTaskURI = taskURI.resolve(nextTaskName);
				flowDirective.set(null);
				output = taskInput;
				output = extensionService.before(arguments, nextTaskName, nextTaskURI, nextTask, output, flowDirective);
				output = taskService.start(arguments, nextTaskName, nextTaskURI, nextTask, output, flowDirective);
				output = extensionService.after(arguments, nextTaskName, nextTaskURI, nextTask, output, flowDirective);
			}
		}
		arguments.setContext(parentContext);
		return output;
	}

}
