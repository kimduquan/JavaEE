package epf.workflow.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.net.URI;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.DoService;
import epf.workflow.spi.ForService;
import epf.workflow.spi.RuntimeExpressionsService;
import epf.workflow.task.schema.ForTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ForServiceImpl implements ForService {
	
	@Inject
	transient RuntimeExpressionsService runtimeExpressionsService;
	
	@Inject
	transient DoService doService;

	@Override
	public Object _for(final RuntimeExpressionArguments arguments, final ForTask task, final Object taskInput, final AtomicBoolean end) throws Error {
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
			doService.do_(task.getDo_(), arguments, taskURI, end);
			if(end.get()) {
				break;
			}
			at++;
		}
		arguments.setContext(parentContext);
		return null;
	}

}
