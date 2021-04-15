package epf.webapp.error;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author PC
 *
 */
public class HandlerFactory extends ExceptionHandlerFactory {
	
	/**
	 * @param wrapped
	 */
	public HandlerFactory(final ExceptionHandlerFactory wrapped) {
        super(wrapped);
    }

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new Handler(getWrapped().getExceptionHandler());
	}

}
