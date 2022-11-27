package epf.client.file;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.CACHE + "/" + Naming.FILE)
public interface FileCache {

	/**
	 * @param paths
	 * @param uriInfo
	 * @param input
	 * @param security
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response putFile(
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
	
	/**
	 * @param client
	 * @param input
	 * @param paths
	 * @return
	 */
	static Response putFile(final Client client, final InputStream input, final java.nio.file.Path paths) {
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
    StreamingOutput getFile(
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
	
	/**
     * @param client
     * @param paths
     * @return
     */
    static InputStream getFile(final Client client, final java.nio.file.Path paths) {
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
}