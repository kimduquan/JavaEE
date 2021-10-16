/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@Provider
public class ExceptionHandler implements 
        Serializable,
        ExceptionMapper<Exception> {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class.getName());
    
    @Override
    public Response toResponse(final Exception exception) {
    	LOGGER.throwing(getClass().getName(), "toResponse", exception);
    	ResponseBuilder builder;
    	if(exception instanceof WebApplicationException) {
    		final WebApplicationException error = (WebApplicationException) exception;
    		builder = Response.fromResponse(error.getResponse());
    	}
    	else if(exception instanceof TimeoutException){
            builder = Response.status(Response.Status.REQUEST_TIMEOUT);
        }
        else if(exception instanceof BulkheadException){
            builder = Response.status(Response.Status.TOO_MANY_REQUESTS);
        }
        else if(exception instanceof CircuitBreakerOpenException){
            builder = Response.status(Response.Status.SERVICE_UNAVAILABLE);
        }
    	else {
    		builder = Response.serverError();
    	}
        return builder.build();
    }
}
