package epf.persistence;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import epf.util.logging.LogManager;

@Provider
public class ExceptionHelper implements ExceptionMapper<Exception>, Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHelper.class.getName());

    @Override
    public Response toResponse(final Exception exception) {
        return handle(exception);
    }
    
    private static boolean isConnectionException(final String sqlState) {
    	return sqlState.startsWith("08");
    }
    
    private static boolean isDataException(final String sqlState) {
    	return sqlState.startsWith("22");
    }
    
    private static boolean isIntegrityConstraintViolation(final String sqlState) {
    	return sqlState.startsWith("23");
    }
    
    private static boolean isInvalidAuthorizationSpecification(final String sqlState) {
    	return sqlState.startsWith("28");
    }
    
    private static boolean isTransactionRollback(final String sqlState) {
    	return sqlState.startsWith("40");
    }
    
    private static boolean isSyntaxErrorOrAccessRuleViolation(final String sqlState) {
    	return sqlState.startsWith("42");
    }
    
    private static boolean isInsufficientResources(final String sqlState) {
    	return sqlState.startsWith("53");
    }
    
    private static boolean isProgramLimitExceeded(final String sqlState) {
    	return sqlState.startsWith("54");
    }
    
    private static boolean isObjectNotInPrerequisiteState(final String sqlState) {
    	return sqlState.startsWith("55");
    }
    
    private static boolean isOperatorIntervention(final String sqlState) {
    	return sqlState.startsWith("55");
    }
    
    private static boolean map(final Throwable failure, final Response.ResponseBuilder builder){
    	boolean map = false;
        if(failure instanceof SQLException) {
        	final SQLException sqlException = (SQLException) failure.getCause();
        	final String sqlState = sqlException.getSQLState();
        	map = true;
        	if(isConnectionException(sqlState)) {
        		builder.status(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), sqlState);
        	}
        	else if(isDataException(sqlState)) {
        		builder.status(Response.Status.BAD_REQUEST.getStatusCode(), sqlState);
        	}
        	else if(isIntegrityConstraintViolation(sqlState)) {
        		builder.status(Response.Status.BAD_REQUEST.getStatusCode(), sqlState);
        	}
        	else if(isInvalidAuthorizationSpecification(sqlState)) {
        		builder.status(Response.Status.UNAUTHORIZED.getStatusCode(), sqlState);
        	}
        	else if(isTransactionRollback(sqlState)) {
        		builder.status(Response.Status.CONFLICT.getStatusCode(), sqlState);
        	}
        	else if(isSyntaxErrorOrAccessRuleViolation(sqlState)) {
        		builder.status(Response.Status.BAD_REQUEST.getStatusCode(), sqlState);
        	}
        	else if(isInsufficientResources(sqlState)) {
        		builder.status(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), sqlState);
        	}
        	else if(isProgramLimitExceeded(sqlState)) {
        		builder.status(Response.Status.TOO_MANY_REQUESTS.getStatusCode(), sqlState);
        	}
        	else if(isObjectNotInPrerequisiteState(sqlState)) {
        		builder.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), sqlState);
        	}
        	else if(isOperatorIntervention(sqlState)) {
        		builder.status(Response.Status.GONE.getStatusCode(), sqlState);
        	}
        	else {
        		map = false;
        	}
        }
        else if(failure instanceof WebApplicationException){
        	final WebApplicationException exception = (WebApplicationException)failure;
        	final int status = exception.getResponse().getStatusInfo().getStatusCode();
            final String reasonPhrase = failure.getMessage();
            builder.status(status, reasonPhrase);
            map = true;
        }
        return map;
    }
    
    private static Response handle(final Throwable failure){
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
        		LOGGER.log(Level.SEVERE, "[ExceptionHelper][handle]", failure);
        	}
        }
        return builder.build();
    }
}
