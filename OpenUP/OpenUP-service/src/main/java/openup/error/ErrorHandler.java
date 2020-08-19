/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.error;

import java.io.Serializable;
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
        Response.Status status = Response.Status.UNAUTHORIZED;
        if(context.getFailure() instanceof TimeoutException){
            status = Response.Status.REQUEST_TIMEOUT;
        }
        else if(context.getFailure() instanceof BulkheadException){
            status = Response.Status.TOO_MANY_REQUESTS;
        }
        else if(context.getFailure() instanceof CircuitBreakerOpenException){
            status = Response.Status.SERVICE_UNAVAILABLE;
        }
        return CompletableFuture.completedStage(Response.status(status).build());
    }
    
}
