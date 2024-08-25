package epf.gateway;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ResponseProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@Provider
public class ExceptionHandler implements 
        Serializable,
        ExceptionMapper<Throwable> {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class.getName());
    
    @Override
    public Response toResponse(final Throwable exception) {
    	ResponseBuilder builder;
    	if(exception instanceof WebApplicationException) {
    		final WebApplicationException error = (WebApplicationException) exception;
    		builder = Response.fromResponse(error.getResponse());
    	}
    	else if(exception instanceof ResponseProcessingException) {
    		final ResponseProcessingException error = (ResponseProcessingException) exception;
    		builder = Response.fromResponse(error.getResponse());
    	}
    	else {
    		builder = Response.serverError();
        	LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
    	}
        return builder.build();
    }
}
