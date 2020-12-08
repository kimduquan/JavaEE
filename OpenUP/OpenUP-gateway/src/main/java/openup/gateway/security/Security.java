/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.security;

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
import openup.gateway.Request;
import org.eclipse.microprofile.faulttolerance.Asynchronous;

/**
 *
 * @author FOXCONN
 */
@Path("security")
@RequestScoped
public class Security {
    
    @Inject
    private Request request;
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> login(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            InputStream in) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("POST", in);
    }
    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Asynchronous
    public CompletionStage<Response> runAs(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            InputStream in) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("PUT", in);
    }
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public CompletionStage<Response> logOut(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("DELETE", null);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> authenticate(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("GET", null);
    }
}
