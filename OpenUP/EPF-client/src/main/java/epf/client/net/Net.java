package epf.client.net;

import java.io.InputStream;
import java.net.URL;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
