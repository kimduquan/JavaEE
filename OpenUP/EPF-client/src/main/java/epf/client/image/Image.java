package epf.client.image;

import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.IMAGE)
public interface Image {
	
	@Path("contours")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	Response findContours(final InputStream input) throws Exception;
	
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
