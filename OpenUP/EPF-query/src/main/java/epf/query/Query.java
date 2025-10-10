package epf.query;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import epf.naming.Naming;
import epf.query.cache.CacheEntry;
import epf.query.client.Entity;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import io.smallrye.common.annotation.RunOnVirtualThread;

@ApplicationScoped
@Path(Naming.QUERY)
public class Query {

	@Inject
	transient EntityCache entityCache;
	
	@Inject
	transient QueryCache queryCache;
	
	@GET
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
    public Response getEntity(
    		@PathParam(Naming.SCHEMA)
            @NotNull
            @NotBlank
            final String schema,
            @PathParam(Naming.Query.ENTITY)
            @NotNull
            @NotBlank
            final String name,
            @PathParam(Naming.Query.ID)
            @NotNull
            @NotBlank
            final String id) throws Exception {
		final CacheEntry entry = entityCache.getEntity(schema, name, id);
		return Response.ok(entry.getValue()).build();
	}

	@HEAD
	@Path("entity/{schema}/{entity}")
	@RunOnVirtualThread
    public Response countEntity(
    		@PathParam(Naming.SCHEMA)
            @NotNull
            @NotBlank
            final String schema,
            @PathParam(Naming.Query.ENTITY)
            @NotNull
            @NotBlank
            final String entity) throws Exception {
		final Integer count = entityCache.countEntity(schema, entity);
		return Response.ok().header(Naming.Query.ENTITY_COUNT, count).build();
	}

	@GET
    @Path("query/{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response executeQuery(
    		@PathParam(Naming.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @QueryParam(Naming.Query.Client.FIRST)
            final Integer firstResult,
            @QueryParam(Naming.Query.Client.MAX)
            final Integer maxResults,
            @QueryParam(Naming.Query.Client.SORT)
    		final List<String> sort
            ) throws Exception {
		if(!paths.isEmpty()) {
			final List<Entity> queryResult = queryCache.executeQuery(schema);
			return Response.ok(queryResult).header(Naming.Query.ENTITY_COUNT, queryResult.size()).build();
		}
		throw new NotFoundException();
	}

	@HEAD
    @Path("query/{schema}/{criteria: .+}")
	@RunOnVirtualThread
	public Response executeCountQuery(
    		@PathParam(Naming.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception {
		final Object count = queryCache.executeCountQuery(schema);
    	return Response.ok().header(Naming.Query.ENTITY_COUNT, count).build();
	}
}
