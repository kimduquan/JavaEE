package epf.webapp.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author PC
 *
 */
public class ExceptionUtil extends ExceptionHandlerFactory {
	
	/**
	 * @param factory
	 */
	public ExceptionUtil(final ExceptionHandlerFactory factory) {
		super(factory);
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new ExceptionHelper(this.getWrapped().getExceptionHandler());
	}

}
