package epf.workflow.task.internal;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;
import epf.workflow.schema.Catch;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.Try;
import epf.workflow.task.DoService;
import epf.workflow.task.TryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TryServiceImpl implements TryService {
	
	@Inject
	transient DoService doService;

	@Override
	public Object _try(final RuntimeExpressionArguments arguments, final Try task, final Object taskInput, final AtomicReference<String> flowDirective) throws Exception {
		final URI taskURI = URI.create(arguments.getTask().getReference());
		try {
			return doService.do_(task.getTry(), arguments, taskURI, flowDirective);
		}
		catch(Exception ex) {
			final Error error = new Error();
			if(catch_(task.getCatch(), error)) {
				return doService.do_(task.getCatch().getDo(), arguments, taskURI, flowDirective);
			}
			else {
				throw ex;
			}
		}
	}
	
	private boolean catch_(final Catch catch_, final Error error) {
		if(catch_.getErrors().getDetail() != null && !catch_.getErrors().getDetail().equals(error.getDetail())) {
			return false;
		}
		if(catch_.getErrors().getStatus() != null && catch_.getErrors().getStatus() != error.getStatus()) {
			return false;
		}
		if(catch_.getErrors().getTitle() != null && !catch_.getErrors().getTitle().equals(error.getTitle())) {
			return false;
		}
		if(catch_.getErrors().getType() != null && !catch_.getErrors().getType().equals(error.getType())) {
			return false;
		}
		return true;
	}
}
