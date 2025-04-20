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
import jakarta.ws.rs.MatrixParam;
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
import epf.naming.Naming;
import epf.naming.Naming.Query.Client;
import epf.query.client.EntityId;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.persistence.QueryPersistence;
import epf.query.util.LinkUtil;
import epf.schema.utility.Request;
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
	Request request;
	
	@GET
    @Path(Naming.Query.Client.ENTITY_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
    public Response getEntity(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
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
		request.setSchema(schema);
		request.setTenant(tenant);
		final Optional<Object> entity = entityCache.getEntity(name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@HEAD
	@Path("entity/{schema}/{entity}")
	@RunOnVirtualThread
    public Response countEntity(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotNull
            @NotBlank
            final String schema,
            @PathParam(Naming.Query.Client.ENTITY)
            @NotNull
            @NotBlank
            final String entity
            ) {
		request.setSchema(schema);
		request.setTenant(tenant);
		final Optional<Integer> count = queryCache.countEntity(entity);
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
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
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
		request.setSchema(schema);
		request.setTenant(tenant);
		if(!paths.isEmpty()) {
			final List<?> resultList = persistence.executeQuery(paths, firstResult, maxResults, context, sort);
			return Response.ok(resultList).header(Client.ENTITY_COUNT, resultList.size()).build();
		}
		throw new NotFoundException();
	}

	@HEAD
    @Path("query/{schema}/{criteria: .+}")
	@RunOnVirtualThread
	public Response executeCountQuery(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @Context
            final SecurityContext context
            ) throws Exception {
		request.setSchema(schema);
		request.setTenant(tenant);
		if(!paths.isEmpty()) {
			final Object count = persistence.executeCountQuery(paths, context);
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
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		final List<EntityId> entityIds) {
		request.setTenant(tenant);
		final List<Object> entities = entityCache.getEntities(entityIds);
		ResponseBuilder response = Response.ok(entities).header(Client.ENTITY_COUNT, entities.size());
		response = LinkUtil.links(response, "", entityIds);
		return response.build();
	}
	
	@Path(Naming.Query.SEARCH)
	public Search getSearch() {
		return search;
	}
}
