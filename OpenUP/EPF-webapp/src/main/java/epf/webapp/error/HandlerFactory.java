package epf.webapp.error;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class HandlerFactory extends ExceptionHandlerFactory {
	
	public HandlerFactory(ExceptionHandlerFactory wrapped) {
        super(wrapped);
    }

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new Handler(getWrapped().getExceptionHandler());
	}

}
