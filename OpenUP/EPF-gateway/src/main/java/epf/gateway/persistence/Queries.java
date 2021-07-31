/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.persistence;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
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
            ) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> search(
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
            ) {
    	request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
}
