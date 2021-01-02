/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.file;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
import openup.gateway.Request;
import org.eclipse.microprofile.faulttolerance.Asynchronous;

/**
 *
 * @author FOXCONN
 */
@Path("files/directories")
@RequestScoped
public class Directories {
    
    @Inject
    private Request request;
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> createDirectories(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            InputStream in
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.POST, in);
    }
    
    @POST
    @Path("temp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> createTempDirectory(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            InputStream in
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.POST, in);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> list(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo
    ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request(HttpMethod.GET, null);
    }
}
