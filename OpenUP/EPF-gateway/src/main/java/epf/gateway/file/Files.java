/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.gateway.file;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.FILE)
@ApplicationScoped
public class Files {
    
    /**
     * 
     */
    @Inject
    transient Request request;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param paths
     * @param body
     * @return
     * @throws Exception 
     */
    @POST
	@Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Asynchronous
    public CompletionStage<Response> createFile(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("paths")
    		final List<PathSegment> paths,
            final InputStream body
    ) throws Exception {
        return request.request(headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param paths
     * @return
     * @throws Exception 
     */
    @GET
    @Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public CompletionStage<Response> lines(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("paths")
    		final List<PathSegment> paths
    ) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param paths
     * @return
     * @throws Exception 
     */
    @DELETE
    @Path("{paths: .+}")
    public CompletionStage<Response> delete(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("paths")
    		final List<PathSegment> paths
    ) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
}
