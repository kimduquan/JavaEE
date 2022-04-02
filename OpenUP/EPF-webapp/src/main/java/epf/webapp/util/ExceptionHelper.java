package epf.webapp.util;

import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.ProtectedViewException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;

/**
 * @author PC
 *
 */
public class ExceptionHelper extends ExceptionHandlerWrapper {
	
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
			String outcome = "/webapp/error?faces-redirect=true";
			if(rootCause instanceof ProtectedViewException) {
				outcome += "&error=" + "ProtectedViewException";
			}
			event.getFacesContext().getApplication().getNavigationHandler().handleNavigation(event.getFacesContext(), null, outcome);
			it.remove();
		}
	}
}
