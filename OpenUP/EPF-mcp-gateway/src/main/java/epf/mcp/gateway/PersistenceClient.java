package epf.mcp.gateway;

import java.util.Map;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = Naming.Persistence.PERSISTENCE_CONFIG)
public interface PersistenceClient {

	@POST
    @Path("persistence/{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	Response persist(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization,
			@PathParam(Naming.Persistence.Client.SCHEMA)
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            final String entity,
            final Map<String, Object> data) throws Exception;
	
	@PUT
    @Path("persistence/{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
	Response merge(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization,
			@PathParam(Naming.Persistence.Client.SCHEMA)
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            final String entity,
            @PathParam(Naming.Persistence.Client.ID)
            final String id,
            final Map<String, Object> data) throws Exception;
	
	@DELETE
    @Path("persistence/{schema}/{entity}/{id}")
	void remove(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization,
			@PathParam(Naming.Persistence.Client.SCHEMA)
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            final String entity,
            @PathParam(Naming.Persistence.Client.ID)
            final String id) throws Exception;
}
