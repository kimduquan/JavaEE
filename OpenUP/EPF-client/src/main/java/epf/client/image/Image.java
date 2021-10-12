/**
 * 
 */
package epf.client.image;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.IMAGE)
public interface Image {
	
	/**
	 * @param input
	 * @return
	 */
	@Path("contours")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	Response findContours(final InputStream input) throws Exception;
	
	/**
	 * @param client
	 * @param input
	 * @return
	 */
	static Response findContours(final Client client, final InputStream input) {
		return client.request(
				target -> target.path("contours"), 
				req -> req.accept(MediaType.APPLICATION_JSON)
				)
				.post(
						Entity.entity(
								input, 
								MediaType.APPLICATION_OCTET_STREAM
								)
						);
	}
}
