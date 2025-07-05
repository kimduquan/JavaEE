package epf.client.script;

import java.io.InputStream;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.SCRIPT)
public interface Script {
	
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	Response eval(
			@QueryParam("lang")
			@NotBlank
			final String lang, 
			final InputStream input,
			@Context
			final SecurityContext security
			);
	
	static <T> T eval(
			final Client client, 
			final String lang, 
			final InputStream input, 
			final Class<T> cls) {
		return client
				.request(
						target -> target.queryParam("lang", lang), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.post(
						Entity.entity(
								input, 
								MediaType.APPLICATION_OCTET_STREAM_TYPE
								)
						)
				.readEntity(cls);
	}
}
