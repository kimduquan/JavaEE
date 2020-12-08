/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.persistence;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import openup.gateway.Request;
import org.eclipse.microprofile.faulttolerance.Asynchronous;

/**
 *
 * @author FOXCONN
 */
@Path("persistence/entity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Entities {
    
    @Inject
    private Request request;
    
    @POST
    @Path("{entity}/{id}")
    @Asynchronous
    public CompletionStage<Response> persist(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            InputStream body) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("POST", body);
    }
    
    @GET
    @Path("{entity}/{id}")
    @Asynchronous
    public CompletionStage<Response> find(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("GET", null);
    }
    
    @DELETE
    @Path("{entity}/{id}")
    @Asynchronous
    public CompletionStage<Response> remove(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("DELETE", null);
    }
}
