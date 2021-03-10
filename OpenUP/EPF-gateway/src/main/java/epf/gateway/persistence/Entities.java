/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.persistence;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
@Path("persistence")
@RequestScoped
public class Entities {
    
    @Inject
    private Request request;
    
    @POST
    @Path("{unit}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> persist(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
            @PathParam("unit") String unit,
            @PathParam("entity") String entity,
            InputStream body) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(body);
    }
    
    @PUT
    @Path("{unit}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> merge(
    		@Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
    		@PathParam("unit") String unit,
            @PathParam("entity") String name,
            @PathParam("id") String id,
            InputStream body
            ) throws Exception {
    	request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(body);
    }
    
    @DELETE
    @Path("{unit}/{entity}/{id}")
    @Asynchronous
    public CompletionStage<Response> remove(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
            @PathParam("unit") String unit,
            @PathParam("entity") String entity,
            @PathParam("id") String id) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
}
