package epf.webapp.util;

import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.ProtectedViewException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
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
			if(rootCause instanceof ProtectedViewException) {
				final String outcome = "/webapp/404.xhtml";
				final FacesContext context = FacesContext.getCurrentInstance();
				context.getApplication().getNavigationHandler().handleNavigation(context, null, outcome);
				it.remove();
			}
		}
	}
}
