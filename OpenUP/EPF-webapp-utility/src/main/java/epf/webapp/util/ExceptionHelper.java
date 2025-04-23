package epf.webapp.util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.faces.FacesException;
import jakarta.faces.application.ProtectedViewException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import epf.util.logging.LogManager;

public class ExceptionHelper extends ExceptionHandlerWrapper {
	
	private transient final static Logger LOGGER = LogManager.getLogger(ExceptionHelper.class.getName());
	
	public ExceptionHelper(final ExceptionHandler wrapped) {
		super(wrapped);
	}
	
	@Override
    public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator();
		while(it.hasNext()) {
			final ExceptionQueuedEvent event = it.next();
			final Throwable exception = event.getContext().getException();
			final Throwable rootCause = getRootCause(exception);
			final FacesContext context = FacesContext.getCurrentInstance();
			if(rootCause instanceof WebAppException) {
				final WebAppException webException = (WebAppException) exception;
				context.getExternalContext().setResponseStatus(webException.getStatus());
			}
			else if(rootCause instanceof ProtectedViewException) {
				context.getExternalContext().setResponseStatus(400);
			}
			else {
				context.getExternalContext().setResponseStatus(500);
			}
			LOGGER.log(Level.SEVERE, "[ExceptionHelper.handle]", exception);
			context.responseComplete();
			it.remove();
		}
	}
}
