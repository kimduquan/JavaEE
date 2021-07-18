/**
 * 
 */
package epf.portlet;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.faces.FacesException;
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
		final Queue<Throwable> throwables = new ConcurrentLinkedQueue<>();
		final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator();
		while(it.hasNext()) {
			final ExceptionQueuedEvent event = it.next();
			throwables.add(getRootCause(event.getContext().getException()));
			it.remove();
		}
		FacesContext.getCurrentInstance().
	}
}
