package epf.query;

import java.util.List;
import java.util.Optional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.util.TenantUtil;
import epf.naming.Naming;
import epf.naming.Naming.Query.Client;
import epf.query.client.EntityId;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.persistence.QueryPersistence;
import epf.query.util.LinkUtil;
import io.smallrye.common.annotation.RunOnVirtualThread;

@ApplicationScoped
@Path(Naming.QUERY)
public class Query {

	@Inject @Readiness
	transient EntityCache entityCache;
	
	@Inject @Readiness
	transient QueryCache queryCache;
	
	@Inject @Readiness
	transient QueryPersistence persistence;
	
	@Inject
	transient Search search;
	
	@Inject
	transient JsonWebToken jwt;
	
	@GET
    @Path(Naming.Query.Client.ENTITY_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
    public Response getEntity(
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotNull
            @NotBlank
            final String schema,
            @PathParam(Naming.Query.Client.ENTITY)
            @NotNull
            @NotBlank
            final String name,
            @PathParam(Naming.Query.Client.ID)
            @NotNull
            @NotBlank
            final String entityId
            ) {
		final String tenant = TenantUtil.getTenantId(jwt);
		final Optional<Object> entity = entityCache.getEntity(tenant, schema, name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@HEAD
	@Path("entity/{schema}/{entity}")
	@RunOnVirtualThread
    public Response countEntity(
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotNull
            @NotBlank
            final String schema,
            @PathParam(Naming.Query.Client.ENTITY)
            @NotNull
            @NotBlank
            final String entity
            ) {
		final String tenant = TenantUtil.getTenantId(jwt);
		final Optional<Integer> count = queryCache.countEntity(tenant, schema, entity);
		if(count.isPresent()) {
			return Response.ok().header(Client.ENTITY_COUNT, count.get()).build();
		}
		throw new NotFoundException();
	}

	@GET
    @Path("query/{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response executeQuery(
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @QueryParam(Naming.Query.Client.FIRST)
            final Integer firstResult,
            @QueryParam(Naming.Query.Client.MAX)
            final Integer maxResults,
            @Context
            final SecurityContext context,
            @QueryParam(Naming.Query.Client.SORT)
    		final List<String> sort
            ) throws Exception {
		if(!paths.isEmpty()) {
			final String tenant = TenantUtil.getTenantId(jwt);
			final List<?> resultList = persistence.executeQuery(tenant, paths, firstResult, maxResults, context, sort);
			return Response.ok(resultList).header(Client.ENTITY_COUNT, resultList.size()).build();
		}
		throw new NotFoundException();
	}

	@HEAD
    @Path("query/{schema}/{criteria: .+}")
	@RunOnVirtualThread
	public Response executeCountQuery(
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @Context
            final SecurityContext context
            ) throws Exception {
		if(!paths.isEmpty()) {
			final String tenant = TenantUtil.getTenantId(jwt);
			final Object count = persistence.executeCountQuery(tenant, paths, context);
	    	return Response.ok().header(Client.ENTITY_COUNT, count).build();
		}
		throw new NotFoundException();
	}

	@PATCH
    @Path(Naming.Query.Client.ENTITY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
    public Response fetchEntities(
    		final List<EntityId> entityIds) {
		final String tenant = TenantUtil.getTenantId(jwt);
		final List<Object> entities = entityCache.getEntities(tenant, entityIds);
		ResponseBuilder response = Response.ok(entities).header(Client.ENTITY_COUNT, entities.size());
		response = LinkUtil.links(response, "", entityIds);
		return response.build();
	}
	
	@Path(Naming.Query.SEARCH)
	public Search getSearch() {
		return search;
	}
}
