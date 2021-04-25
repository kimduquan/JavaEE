package epf.client.script;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
@Path("script")
public interface Script {

	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	void submit();
}
