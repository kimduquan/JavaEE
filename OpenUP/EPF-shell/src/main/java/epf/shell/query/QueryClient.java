package epf.shell.query;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

@Path(Naming.QUERY)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface QueryClient {
	
	@GET
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam(Naming.Query.Client.SCHEMA)
            final String schema,
            @PathParam(Naming.Query.Client.ENTITY)
            final String entity,
            @PathParam(Naming.Query.Client.ID)
            final String entityId
            );
	
	@HEAD
	@Path("entity/{schema}/{entity}")
    Response countEntity(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam(Naming.Query.Client.SCHEMA)
            final String schema,
            @PathParam(Naming.Query.Client.ENTITY)
            final String entity
            );
	
	@PATCH
    @Path(Naming.Query.Client.ENTITY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response fetchEntities(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		final String entityIds);
}
