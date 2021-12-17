/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientException;
import java.util.logging.Logger;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import epf.persistence.internal.h2.H2ErrorCodes;
import epf.persistence.internal.mysql.MySQLErrorCodes;
import epf.util.EPFException;
import epf.util.logging.LogManager;

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
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class.getName());

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
        	if(H2ErrorCodes.NOT_ENOUGH_RIGHTS == errorCode
        			|| H2ErrorCodes.ADMIN_REQUIRED == errorCode) {
        		status = Response.Status.FORBIDDEN;
        	}
        }
        else if(failure instanceof SQLException){
        	final SQLException exception = (SQLException)failure;
        	final int errorCode = exception.getErrorCode();
        	if(MySQLErrorCodes.ER_ACCESS_DENIED_ERROR == errorCode) {
        		status = Response.Status.UNAUTHORIZED;
        	}
        }
        else{
            mapped = false;
        }
        if(mapped){
            final String message = failure.getMessage();
            builder.status(status.getStatusCode(), message);
        }
        return mapped;
    }
    
    /**
     * @param failure
     * @return
     */
    protected static Response handle(final Throwable failure){
    	final ResponseBuilder builder = Response.serverError();
    	if(failure != null){
        	Throwable rootCause = failure;
        	boolean mapStatus = map(failure, builder);
        	if(!mapStatus) {
            	Throwable cause = failure.getCause();
                while(cause != null && !cause.equals(failure)){
                	rootCause = cause;
                	mapStatus = mapStatus || map(cause, builder);
                    cause = cause.getCause();
                }
        	}
        	if(!mapStatus && rootCause != null) {
        		builder.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), rootCause.getMessage());
        		LOGGER.throwing(ExceptionHandler.class.getName(), "handle", failure);
        	}
        }
        return builder.build();
    }
}
