package epf.rules;

import java.io.Serializable;
import java.io.StreamCorruptedException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import epf.util.EPFException;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Response toResponse(final Exception exception) {
        return handle(exception);
    }
    
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
