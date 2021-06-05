/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.rules;

import java.io.Serializable;
import java.io.StreamCorruptedException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import epf.client.EPFException;

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
    @Override
    public Response toResponse(final Exception exception) {
        return handle(exception);
    }
    
    /**
     * @param failure
     * @param builder
     * @return
     */
    protected static boolean map(final Throwable failure, final Response.ResponseBuilder builder){
        Response.StatusType status = Response.Status.INTERNAL_SERVER_ERROR;
        boolean mapped = true;
        if(failure instanceof EPFException) {
        	mapped = false;
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
    protected static Response handle(final Throwable failure){
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
