package epf.management.external;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.management.persistence.schema.PersistenceTenant;
import epf.management.persistence.schema.UpdatePersistenceTenantInfo;
import epf.naming.Naming;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.HttpHeaders;

@RegisterRestClient(configKey = Naming.Management.Internal.PERSISTENCE_MANAGEMENT)
public interface PersistenceClient {

	@PUT
	@Path("tenants/{external_id}")
	PersistenceTenant createOrUpdateTenant(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization, 
			@PathParam("external_id")
			final String external_id, 
			final UpdatePersistenceTenantInfo updateInfo) throws Exception;
}
