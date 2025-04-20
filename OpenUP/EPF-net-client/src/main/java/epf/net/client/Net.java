package epf.net.client;

import java.io.InputStream;
import java.net.URL;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.NET)
public interface Net {
	
	/**
	 * @param url
	 * @return
	 */
	@Path(Naming.Net.URL)
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	Response rewriteUrl(
			@FormParam(Naming.Net.URL) 
			@NotNull
			final URL url) throws Exception;
	
	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@Path(Naming.Net.URL)
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	String shortenUrl(final InputStream body) throws Exception;
	
	/**
	 * @param client
	 * @param url
	 * @return
	 */
	static String rewriteUrl(final Client client, final String url) {
		return client.request(
    			target -> target.path(Naming.Net.URL), 
    			req -> req
    			)
    			.post(Entity.form(new Form().param(Naming.Net.URL, url)), String.class);
	}
}
