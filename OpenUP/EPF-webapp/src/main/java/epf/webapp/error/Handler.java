package epf.webapp.error;

import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class Handler extends ExceptionHandlerWrapper {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Handler.class.getName());

	/**
	 * @param wrapped
	 */
	public Handler(final ExceptionHandler wrapped) {
        super(wrapped);
    }
	
	@Override
    public void handle() throws FacesException {
		ExceptionQueuedEvent event = null;
		WebApplicationException exception = null;
		if(getUnhandledExceptionQueuedEvents().iterator().hasNext()) {
			event = getUnhandledExceptionQueuedEvents().iterator().next();
			if(event.getContext().getException() instanceof WebApplicationException) {
				exception = (WebApplicationException) event.getContext().getException();
			}
		}
		if(exception != null && event != null && event.getContext().getContext().getExternalContext().getResponse() instanceof HttpServletResponse) {
			final HttpServletResponse response = (HttpServletResponse)event.getContext().getContext().getExternalContext().getResponse();
			try {
				getUnhandledExceptionQueuedEvents().iterator().remove();
				response.sendError(exception.getResponse().getStatus());
			} 
			catch (Exception e) {
				LOGGER.throwing(LOGGER.getName(), "handle", e);
			}
		}
		super.handle();
    }
}
