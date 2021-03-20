/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service;

import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

/**
 *
 * @author FOXCONN
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception>, Serializable {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static final int NOT_ENOUGH_RIGHTS_FOR_1 = 90_096;
    /**
     * 
     */
    private static final int ADMIN_RIGHTS_REQUIRED = 90_040;

    /**
     *
     */
    @Override
    public Response toResponse(final Exception exception) {
        return handle(exception);
    }
    
    /**
     * @param failure
     * @param builder
     * @return
     */
    protected boolean map(final Throwable failure, final Response.ResponseBuilder builder){
        Response.StatusType status = Response.Status.INTERNAL_SERVER_ERROR;
        boolean mapped = true;
        if(failure instanceof ServiceException) {
        	mapped = false;
        }
        else if(failure instanceof TimeoutException){
            status = Response.Status.REQUEST_TIMEOUT;
        }
        else if(failure instanceof BulkheadException){
            status = Response.Status.TOO_MANY_REQUESTS;
        }
        else if(failure instanceof CircuitBreakerOpenException){
            status = Response.Status.SERVICE_UNAVAILABLE;
        }
        else if(failure instanceof SQLInvalidAuthorizationSpecException){
            status = Response.Status.UNAUTHORIZED;
        }
        else if(failure instanceof java.util.concurrent.TimeoutException){
            status = Response.Status.REQUEST_TIMEOUT;
        }
        else if(failure instanceof ValidationException){
            status = Response.Status.BAD_REQUEST;
        }
        else if(failure instanceof WebApplicationException){
        	final WebApplicationException exception = (WebApplicationException)failure;
            status = exception.getResponse().getStatusInfo();
        }
        else if(failure instanceof StreamCorruptedException){
            mapped = true;
        }
        else if(failure instanceof SQLNonTransientException){
        	final SQLNonTransientException exception = (SQLNonTransientException)failure;
        	final int errorCode = exception.getErrorCode();
        	if(NOT_ENOUGH_RIGHTS_FOR_1 == errorCode
        			|| ADMIN_RIGHTS_REQUIRED == errorCode) {
        		status = Response.Status.FORBIDDEN;
        	}
        }
        else{
            mapped = false;
        }
        if(mapped){
            builder.status(status);
        }
        return mapped;
    }
    
    /**
     * @param failure
     * @return
     */
    protected Response handle(final Throwable failure){
    	final ResponseBuilder builder = Response.serverError();
        if(failure != null && !map(failure, builder)){
        	Throwable cause = failure.getCause();
            while(cause != null && !cause.equals(failure)){
                map(cause, builder);
                cause = cause.getCause();
            }
        }
        return builder.build();
    }
}
