package epf.file;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import epf.util.logging.LogManager;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception>, Serializable {

    private static final long serialVersionUID = 1L;
    
    private static transient final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class.getName());

    @Override
    public Response toResponse(final Exception exception) {
        final Response response = handle(exception);
        if(response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
        	LOGGER.log(Level.SEVERE, "[ExceptionHandler.toResponse]", exception);
        }
        return response;
    }
    
    protected static boolean map(final Throwable failure, final Response.ResponseBuilder builder){
        Response.StatusType status = Response.Status.INTERNAL_SERVER_ERROR;
        boolean mapped = true;
        if(failure instanceof java.util.concurrent.TimeoutException){
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
        else if(failure instanceof NoSuchFileException) {
        	status = Response.Status.NOT_FOUND;
        }
        else if(failure instanceof AccessDeniedException) {
        	status = Response.Status.FORBIDDEN;
        }
        else if(failure instanceof InvalidPathException) {
        	status = Response.Status.BAD_REQUEST;
        }
        else if(failure instanceof FileNotFoundException) {
        	status = Response.Status.NOT_FOUND;
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
