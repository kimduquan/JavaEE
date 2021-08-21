/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.persistence;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Path("persistence")
public class Queries {
    
    /**
     * 
     */
    @Inject
    private transient Request request;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param paths
     * @return
     * @throws Exception 
     */
    @GET
    @Path("{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getCriteriaQueryResult(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("criteria")
            final List<PathSegment> paths
            ) throws Exception {
        return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, null));
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> search(
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
            ) throws Exception {
    	return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, null));
    }
}
