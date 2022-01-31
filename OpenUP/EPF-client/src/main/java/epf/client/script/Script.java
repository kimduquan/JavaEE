package epf.client.script;

import java.io.InputStream;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SCRIPT)
public interface Script {
	
	/**
	 * 
	 */
	String ROOT = "epf.script.root";

	/**
	 * @param lang
	 * @param input
	 * @param security
	 * @return
	 */
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
	
	/**
	 * @param client
	 * @param lang
	 * @param input
	 * @param cls
	 */
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
