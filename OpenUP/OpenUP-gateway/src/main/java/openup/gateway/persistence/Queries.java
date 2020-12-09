/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.persistence;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import openup.gateway.Request;
import org.eclipse.microprofile.faulttolerance.Asynchronous;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("persistence")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Queries {
    
    @Inject
    private Request request;
    
    @GET
    @Path("query/{criteria: .+}")
    @Asynchronous
    public CompletionStage<Response> getCriteriaQueryResult(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @PathParam("criteria")
            List<PathSegment> paths
            ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("GET", null);
    }
    
    @GET
    @Path("queries/{query}")
    @Asynchronous
    public CompletionStage<Response> getNamedQueryResult(
            @Context HttpHeaders headers, 
            @Context UriInfo uriInfo,
            @PathParam("query")
            String name
            ) throws Exception{
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        return request.request("GET", null);
    }
}
