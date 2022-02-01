package epf.persistence.client;

import java.io.InputStream;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import io.smallrye.mutiny.Uni;

/**
 * @author PC
 *
 */
@Path(Naming.PERSISTENCE)
public interface RxPersistence {

	/**
	 * @param schema
	 * @param entity
	 * @param context
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Object> persist(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @Context
            final SecurityContext context,
            @NotNull
            final InputStream body
            ) throws Exception;
	
	/**
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @param context
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Void> merge(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotBlank
            final String entityId,
            @Context
            final SecurityContext context,
            @NotNull
            final InputStream body
            ) throws Exception;
	
	/**
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @param context
	 * @return
	 */
	@DELETE
    @Path("{schema}/{entity}/{id}")
    Uni<Void> remove(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotBlank
            final String entityId,
            @Context
            final SecurityContext context
            );
	
	/**
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @param context
	 * @return
	 */
	@POST
    @Path("{schema}/{entity}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Response> find(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotBlank
            final String entityId,
            @Context
            final SecurityContext context
            );
	
	/**
	 * @param schema
	 * @param paths
	 * @param firstResult
	 * @param maxResults
	 * @param context
	 * @return
	 */
	@GET
    @Path("{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Response> executeQuery(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @QueryParam("first")
            final Integer firstResult,
            @QueryParam("max")
            final Integer maxResults,
            @Context
            final SecurityContext context
            );
}
