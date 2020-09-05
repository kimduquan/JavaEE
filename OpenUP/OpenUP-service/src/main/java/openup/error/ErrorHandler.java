/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.error;

import java.io.Serializable;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

/**
 *
 * @author FOXCONN
 */
public class ErrorHandler implements FallbackHandler<CompletionStage<Response>>, Serializable {

    @Override
    public CompletionStage<Response> handle(ExecutionContext context) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        Throwable failure = context.getFailure();
        if(failure instanceof TimeoutException){
            status = Response.Status.REQUEST_TIMEOUT;
        }
        else if(failure instanceof BulkheadException){
            status = Response.Status.TOO_MANY_REQUESTS;
        }
        else if(failure instanceof CircuitBreakerOpenException){
            status = Response.Status.SERVICE_UNAVAILABLE;
        }
        else {
            Throwable cause = failure.getCause();
            Throwable rootCause = failure;
            while(cause != null){
                rootCause = cause;
                cause = cause.getCause();
            }
            if(rootCause instanceof SQLInvalidAuthorizationSpecException){
                status = Response.Status.UNAUTHORIZED;
            }
        }
        return CompletableFuture.completedStage(Response.status(status).build());
    }
    
}
