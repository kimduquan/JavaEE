package epf.gateway;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
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
    	LOGGER.log(Level.SEVERE, "toResponse", exception);
    	ResponseBuilder builder;
    	if(exception instanceof WebApplicationException) {
    		final WebApplicationException error = (WebApplicationException) exception;
    		builder = Response.fromResponse(error.getResponse());
    	}
    	else {
    		builder = Response.serverError();
    	}
        return builder.build();
    }
}
