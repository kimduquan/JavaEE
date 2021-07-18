/**
 * 
 */
package epf.portlet;

import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
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
			final FacesMessage msg = new FacesMessage(exception.getLocalizedMessage(), rootCause.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			it.remove();
		}
	}
}
