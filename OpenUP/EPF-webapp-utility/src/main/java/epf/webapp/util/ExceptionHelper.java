package epf.webapp.util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProtectedViewException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class ExceptionHelper extends ExceptionHandlerWrapper {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(ExceptionHelper.class.getName());
	
	/**
	 * @param wrapped
	 */
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
