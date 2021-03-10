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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
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
    
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(Exception ex) {
        return handle(ex);
    }
    
    boolean map(Throwable failure, Response.ResponseBuilder builder){
        Response.StatusType status = Response.Status.INTERNAL_SERVER_ERROR;
        boolean mapped = true;
        logger.log(Level.SEVERE, failure.getMessage(), failure);
        if(failure instanceof TimeoutException){
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
            WebApplicationException ex = (WebApplicationException)failure;
            status = ex.getResponse().getStatusInfo();
        }
        else if(failure instanceof StreamCorruptedException){
            
        }
        else if(failure instanceof SQLNonTransientException){
        	SQLNonTransientException ex = (SQLNonTransientException)failure;
        	int errorCode = ex.getErrorCode();
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
    
    Response handle(Throwable failure){
        ResponseBuilder builder = Response.serverError();
        if(failure != null){
            if(map(failure, builder) == false){
                Throwable cause = failure.getCause();
                while(cause != null && failure != cause){
                    map(cause, builder);
                    cause = cause.getCause();
                }
            }
        }
        return builder.build();
    }
    
    private static final int NOT_ENOUGH_RIGHTS_FOR_1 = 90096;
    private static final int ADMIN_RIGHTS_REQUIRED = 90040;
}
