package epf.client.net;

import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
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
	@Path("url")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	String rewriteUrl(
			@FormParam("url") 
			@NotNull
			final URL url,
			@Context 
			final HttpServletRequest request,
			@Context
			final SecurityContext security) throws Exception;
	
	/**
	 * @param client
	 * @param url
	 * @return
	 */
	static String rewriteUrl(final Client client, final String url) {
		return client.request(
    			target -> target.path("url"), 
    			req -> req.accept(MediaType.TEXT_PLAIN)
    			)
    			.post(Entity.form(new Form().param("url", url)), String.class);
	}
}
