package epf.webapp.util;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class ExceptionUtil extends ExceptionHandlerFactory {
	
	public ExceptionUtil(final ExceptionHandlerFactory factory) {
		super(factory);
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new ExceptionHelper(this.getWrapped().getExceptionHandler());
	}

}
