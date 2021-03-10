/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.file;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("file")
@RequestScoped
public class Files {
    
    @Inject
    private Request request;
    
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> copy(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
            InputStream in
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(in);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> createFile(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
            InputStream in
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(in);
    }
    
    @POST
    @Path("temp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> createTempFile(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
            InputStream in
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(in);
    }
    
    @DELETE
    @Asynchronous
    public CompletionStage<Response> delete(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> find(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req
            ) 
            throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
    
    @GET
    @Path("lines")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Asynchronous
    public CompletionStage<Response> lines(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req
            ) 
            throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> move(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @Context javax.ws.rs.core.Request req,
            InputStream in
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(in);
    }
}
