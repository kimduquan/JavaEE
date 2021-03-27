/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.file;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author FOXCONN
 */
@Path("file")
public interface Files {
	
	@POST
	@Path("{paths: .+}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	Response createFile(
			@PathParam("paths")
			final List<PathSegment> paths,
			@Context 
    		final UriInfo uriInfo,
			@Context 
			final HttpServletRequest request,
			@Context
			final SecurityContext security
			);
	
    @GET
    @Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    StreamingOutput lines(
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		final List<PathSegment> paths,
    		@Context
			final SecurityContext security
    		);
    
    @DELETE
    @Path("{paths: .+}")
    Response delete(
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		final List<PathSegment> paths,
    		@Context
			final SecurityContext security);
}
