package epf.persistence.util;

import java.io.Serializable;
import java.sql.SQLException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

public class SQLStateExceptionMapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static boolean checkSQLState(final String sqlState, final String sqlStateClass) {
		final String prefix = sqlStateClass.substring(2);
		return sqlState.startsWith(prefix);
	}
    
    private static boolean mapResponseStatus(final Throwable failure, final Response.ResponseBuilder builder){
    	boolean map = false;
        if(failure instanceof SQLException) {
        	final SQLException sqlException = (SQLException) failure;
        	final String sqlState = sqlException.getSQLState();
        	map = true;
        	if(checkSQLState(sqlState, SQLStateClasses.connection_exception)) {
        		builder.status(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.data_exception)) {
        		builder.status(Response.Status.BAD_REQUEST.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.integrity_constraint_violation)) {
        		builder.status(Response.Status.BAD_REQUEST.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.invalid_authorization_specification)) {
        		builder.status(Response.Status.UNAUTHORIZED.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.transaction_rollback)) {
        		builder.status(Response.Status.CONFLICT.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.syntax_error_or_access_rule_violation)) {
        		builder.status(Response.Status.BAD_REQUEST.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.insufficient_resources)) {
        		builder.status(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.program_limit_exceeded)) {
        		builder.status(Response.Status.TOO_MANY_REQUESTS.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.object_not_in_prerequisite_state)) {
        		builder.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), sqlState);
        	}
        	else if(checkSQLState(sqlState, SQLStateClasses.operator_intervention)) {
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
    
    public Response toResponse(final Exception failure){
    	final ResponseBuilder builder = Response.serverError();
    	if(failure != null){
        	Throwable rootCause = failure;
        	boolean mapStatus = mapResponseStatus(failure, builder);
        	if(!mapStatus) {
            	Throwable cause = failure.getCause();
                while(cause != null && !cause.equals(failure)){
                	rootCause = cause;
                	mapStatus = mapStatus || mapResponseStatus(cause, builder);
                    cause = cause.getCause();
                }
        	}
        	if(!mapStatus && rootCause != null) {
        		builder.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), rootCause.getMessage());
        	}
        }
        return builder.build();
    }
}
