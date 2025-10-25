package epf.query;

import java.util.List;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import epf.management.util.OrganizationUtil;
import epf.naming.Naming;
import epf.persistence.util.EntityTypeUtil;
import epf.query.cache.CacheEntry;
import epf.query.cache.PersistenceCache;
import epf.query.cache.QueryCache;
import io.smallrye.common.annotation.RunOnVirtualThread;

@ApplicationScoped
@Path(Naming.QUERY)
public class Query {
	
	@Inject
	transient EntityManager manager;
	
	@Inject
	transient QueryCache queryCache;
	
	@Inject
	transient PersistenceCache persistenceCache;
	
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
            final String id,
            @Context
            final JsonWebToken jwt) throws Exception {
		final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, name).orElseThrow(NotFoundException::new);
		final CacheEntry entry = queryCache.getEntity(organizationId, schema, name, id, entityType);
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
            final String name,
            @Context
            final JsonWebToken jwt) throws Exception {
		final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, name).orElseThrow(NotFoundException::new);
		final Long count = queryCache.countEntity(organizationId, schema, name, entityType);
		return Response.ok().header(Naming.Query.COUNT, count).build();
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
    		final List<String> sort,
            @Context
            final JsonWebToken jwt) throws Exception {
		final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		if(!paths.isEmpty()) {
			final List<?> queryResult = queryCache.executeQuery(organizationId, schema, paths.toArray(new PathSegment[0]), firstResult, maxResults, sort.toArray(new String[0]));
			persistenceCache.clearCountQuery(organizationId, schema, paths.toArray(new PathSegment[0]));
			return Response.ok(queryResult).header(Naming.Query.COUNT, queryResult.size()).build();
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
            final List<PathSegment> paths,
            @Context
            final JsonWebToken jwt) throws Exception {
		final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final Long count = queryCache.executeCountQuery(organizationId, schema, paths.toArray(new PathSegment[0]));
    	return Response.ok().header(Naming.Query.COUNT, count).build();
	}
}
