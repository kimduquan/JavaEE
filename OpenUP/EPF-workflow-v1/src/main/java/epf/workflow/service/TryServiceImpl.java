package epf.workflow.service;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;
import epf.workflow.schema.Catch;
import epf.workflow.schema.Error;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.DoService;
import epf.workflow.spi.TryService;
import epf.workflow.task.schema.TryTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TryServiceImpl implements TryService {
	
	@Inject
	transient DoService doService;

	@Override
	public Object _try(final RuntimeExpressionArguments arguments, final TryTask task, final Object taskInput, final AtomicBoolean end) throws Error {
		final URI taskURI = URI.create(arguments.getTask().getReference());
		try {
			return doService.do_(task.getTry_(), arguments, taskURI, end);
		}
		catch(Error error) {
			if(catch_(task.getCatch_(), error)) {
				return doService.do_(task.getCatch_().get_do(), arguments, taskURI, end);
			}
			else {
				throw error;
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
