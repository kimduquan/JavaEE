package epf.lang.internal;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.lang.schema.Request;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * 
 */
@RegisterRestClient(configKey = Naming.Lang.Internal.OLLAMA)
@Path("api")
public interface Ollama {

	/**
	 * @param request
	 * @return
	 */
	@POST
	@Path("chat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	CompletionStage<InputStream> chat(final Request request);
}
