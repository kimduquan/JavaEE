package epf.webapp.internal;

import epf.management.schema.Principal;
import epf.naming.Naming;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path(Naming.MANAGEMENT)
public interface ManagementClient {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Principal authenticate();
}
