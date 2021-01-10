/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.rpc;

import java.lang.invoke.MethodHandles;
import static java.lang.invoke.MethodType.methodType;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author FOXCONN
 */
@Path("rpc")
public interface Server {
    
    public static final String JSON_RPC = "application/vscode-jsonrpc";
    
    @Produces(JSON_RPC)
    @Consumes(JSON_RPC)
    @POST
    default Response request(Request request) throws Exception {
        Response response = new Response();
        response.setId(request.getId());
        response.setJsonrpc("2.0");
        try {
            String method = request.getMethod();
            String[] segments = method.split("/");
            method = segments[segments.length - 1];
            if(request.getParams().isPresent()){
                Object result = MethodHandles.lookup()
                        .bind(
                                this,
                                method,
                                methodType(
                                        Object.class,
                                        request.getParams()
                                                .get()
                                                .getClass()
                                )
                        )
                        .invoke(request.getParams().get());
                response.setResult(Optional.ofNullable(result));
            }
            else{
                Object result = MethodHandles.lookup()
                        .bind(
                                this,
                                method,
                                methodType(Object.class)
                        )
                        .invoke();
                response.setResult(Optional.ofNullable(result));
            }
        }
        catch (Error error){
            response.setError(Optional.of(error));
        }
        catch(NoSuchMethodException ex){
            Error error = new Error();
            error.setCode(ErrorCodes.MethodNotFound);
            error.setMessage(ex.getMessage());
            response.setError(Optional.of(error));
        }
        catch (Throwable ex) {
            Error error = new Error();
            error.setCode(ErrorCodes.InternalError);
            error.setMessage(ex.getMessage());
            response.setError(Optional.of(error));
        }
        return response;
    }
    
    @Produces(JSON_RPC)
    @Consumes(JSON_RPC)
    @PUT
    default void notify(Notification notification) {
        try {
            String method = notification.getMethod();
            String[] segments = method.split("/");
            method = segments[segments.length - 1].substring(2);
            if(notification.getParams().isPresent()){
                MethodHandles.lookup()
                        .bind(
                                this,
                                method,
                                methodType(
                                        Object.class,
                                        notification.getParams().get().getClass()
                                )
                        )
                        .invoke(notification.getParams().get());
            }
            else{
                MethodHandles.lookup()
                        .bind(
                                this,
                                method,
                                methodType(Object.class)
                        )
                        .invoke();
            }
        }
        catch (Throwable ex){
            
        }
    }
}
