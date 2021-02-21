/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.system;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;

/**
 *
 * @author FOXCONN
 */
@Path("system")
@RequestScoped
public class Processes {
    
    @Inject
    private Request request;
    
    @Path("process")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> start(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            InputStream in) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.POST, in);
    }
    
    @Path("process")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getProcesses(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.GET, null);
    }
    
    @Path("process")
    @DELETE
    @Asynchronous
    public CompletionStage<Response> stop(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.DELETE, null);
    }
    
    @GET
    @Path("process")
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> info(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.GET, null);
    }
    
    @DELETE
    @Path("process")
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> destroy(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.DELETE, null);
    }
    
    @GET
    @Path("process/out")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Asynchronous
    public CompletionStage<Response> output(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.GET, null);
    }
}
