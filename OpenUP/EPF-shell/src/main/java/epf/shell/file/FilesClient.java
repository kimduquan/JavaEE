package epf.shell.file;

import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public interface FilesClient {

	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response createFile(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			final InputStream input
			);
	
	@GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response read(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token
    		);
	
	@DELETE
    Response delete(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token);
}
