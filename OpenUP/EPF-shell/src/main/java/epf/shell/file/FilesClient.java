package epf.shell.file;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 * @author PC
 *
 */
@Path("/")
public interface FilesClient {

	/**
	 * @param token
	 * @param input
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response createFile(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			final InputStream input
			);
	
	/**
	 * @param token
	 * @return
	 */
	@GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    StreamingOutput read(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token
    		);
	
	/**
	 * @param token
	 * @return
	 */
	@DELETE
    Response delete(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token);
}
