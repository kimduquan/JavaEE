package epf.webapp.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProtectedViewException;
import javax.faces.application.ViewExpiredException;
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
			if(rootCause instanceof ProtectedViewException) {
				final String outcome = "/404";
				context.getApplication().getNavigationHandler().handleNavigation(context, null, outcome);
				it.remove();
			}
			else if(rootCause instanceof ViewExpiredException) {
				try {
					context.getExternalContext().redirect(epf.webapp.naming.Naming.CONTEXT_ROOT);
				} 
				catch (IOException e) {
					LOGGER.log(Level.SEVERE, "[ExceptionHelper.handle]", e);
				}
			}
		}
		getWrapped().handle();
	}
}
