package epf.gateway.util;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StreamClient {

	@POST
	CompletionStage<InputStream> post(final Object input);
	
	@PUT
	CompletionStage<InputStream> put(final Object input);
	
	@PATCH
	CompletionStage<InputStream> patch(final Object input);
	
	@DELETE
	CompletionStage<InputStream> delete();
	
	@GET
	CompletionStage<InputStream> get();
	
	@HEAD
	CompletionStage<InputStream> head();
	
	@OPTIONS
	CompletionStage<InputStream> options();
}
