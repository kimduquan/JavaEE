package epf.webapp.internal;

import epf.management.schema.Session;
import epf.naming.Naming;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path(Naming.MANAGEMENT)
public interface ManagementClient {

	@POST
	@Path(Naming.Management.SESSION)
	@Produces(MediaType.APPLICATION_JSON)
	Session newSession();
}
