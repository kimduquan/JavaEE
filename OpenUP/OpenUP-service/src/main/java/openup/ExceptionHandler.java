/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import java.io.Serializable;
import java.sql.SQLInvalidAuthorizationSpecException;
import javax.security.enterprise.AuthenticationException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

/**
 *
 * @author FOXCONN
 */
@Provider
public class ExceptionHandler implements 
        FallbackHandler<Response>, 
        Serializable,
        ExceptionMapper<Exception> {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    @Override
    public Response handle(ExecutionContext context) {
        Throwable failure = context.getFailure();
        Response response = handle(failure);
        return response;
    }

    @Override
    public Response toResponse(Exception ex) {
        return handle(ex);
    }
    
    boolean map(Throwable failure, Response.ResponseBuilder builder){
        Response.StatusType status = Response.Status.INTERNAL_SERVER_ERROR;
        boolean mapped = true;
        if(failure instanceof AuthenticationException){
            status = Response.Status.UNAUTHORIZED;
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
            WebApplicationException ex = (WebApplicationException)failure;
            status = ex.getResponse().getStatusInfo();
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
}
