package epf.mcp.gateway;

import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.persistence.schema.EmbeddableType;
import epf.persistence.schema.EntityType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = Naming.Schema.SCHEMA_CONFIG)
@Path(Naming.SCHEMA)
public interface SchemaClient {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	List<EntityType> getEntities(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization) throws Exception;
	
	@GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
	List<EmbeddableType> getEmbeddables(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization) throws Exception;
}
