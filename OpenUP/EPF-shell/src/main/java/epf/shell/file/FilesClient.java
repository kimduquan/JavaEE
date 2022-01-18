package epf.shell.file;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.FILE)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface FilesClient {

	/**
	 * @param paths
	 * @param input
	 * @return
	 */
	@POST
	@Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response createFile(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("paths")
			final List<String> paths,
			final InputStream input
			);
	
	/**
	 * @param paths
	 * @return
	 */
	@GET
    @Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    StreamingOutput read(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("paths")
    		final List<String> paths
    		);
	
	/**
	 * @param paths
	 * @return
	 */
	@DELETE
    @Path("{paths: .+}")
    Response delete(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("paths")
    		final List<String> paths);
}
