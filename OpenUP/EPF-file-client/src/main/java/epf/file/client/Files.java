package epf.file.client;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;
import jakarta.validation.constraints.*;

@Path(Naming.FILE)
public interface Files {
	
	@POST
	@Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response createFile(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("paths")
			@NotEmpty
			final List<PathSegment> paths,
			@Context 
    		final UriInfo uriInfo,
			final InputStream input,
			@Context
			final SecurityContext security
			) throws Exception;
	
	static Response createFile(final Client client, final InputStream input, final java.nio.file.Path paths) {
		return client
				.request(
						target -> { 
							final Iterator<java.nio.file.Path> itPath = paths.iterator();
							while(itPath.hasNext()) {
								target = target.path(itPath.next().toString());
							}
							return target; 
							}, 
						req -> req)
				.post(Entity.entity(input, MediaType.APPLICATION_OCTET_STREAM_TYPE));
	}
	
    @GET
    @Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    StreamingOutput read(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		@NotEmpty
    		final List<PathSegment> paths,
    		@Context
			final SecurityContext security
    		) throws Exception;
    
    static InputStream read(final Client client, final java.nio.file.Path paths) {
    	return client
    			.request(
    					target -> {
    						final Iterator<java.nio.file.Path> itPath = paths.iterator();
							while(itPath.hasNext()) {
								target = target.path(itPath.next().toString());
							}
    						return target;
    					}, 
    					req -> req.accept(MediaType.APPLICATION_OCTET_STREAM)
    					)
    			.get(InputStream.class);
    }
    
    @DELETE
    @Path("{paths: .+}")
    Response delete(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		@NotEmpty
    		final List<PathSegment> paths,
    		@Context
			final SecurityContext security) throws Exception;
    
    static Response delete(final Client client, final java.nio.file.Path paths) {
    	return client
    			.request(
    					target -> {
    						final Iterator<java.nio.file.Path> itPath = paths.iterator();
							while(itPath.hasNext()) {
								target = target.path(itPath.next().toString());
							}
    						return target;
    					}, 
    					req -> req
    					)
    			.delete();
    }
}
