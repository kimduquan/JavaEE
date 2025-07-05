package epf.query.client;

import java.util.List;
import java.util.function.Function;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.QUERY)
public interface Query {
	
	@GET
    @Path(Naming.Query.Client.ENTITY_PATH)
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotNull
            @NotBlank
            final String schema,
            @PathParam(Naming.Query.Client.ENTITY)
            @NotNull
            @NotBlank
            final String entity,
            @PathParam(Naming.Query.Client.ID)
            @NotNull
            @NotBlank
            final String entityId
            );
	
	static <T> T getEntity(final Client client, final Class<T> cls, final String schema, final String entity, final String entityId) {
		return client.request(
    			target -> target.path(Naming.Query.Client.ENTITY).path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(cls);
	}
	
	static Response getEntity(final Client client, final String schema, final String entity, final String entityId) {
		return client.request(
    			target -> target.path(Naming.Query.Client.ENTITY).path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
				.get();
	}
	
	@HEAD
	@Path("entity/{schema}/{entity}")
    Response countEntity(
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
            );
	
	static Integer countEntity(final Client client, final String schema, final String entity) {
		final String count = client.request(
    			target -> target.path(Naming.Query.Client.ENTITY).path(schema).path(entity), 
    			req -> req
    			)
				.head()
				.getHeaderString(Naming.Query.Client.ENTITY_COUNT);
		return Integer.parseInt(count);
	}
	
	@PATCH
    @Path(Naming.Query.Client.ENTITY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response fetchEntities(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		final List<EntityId> entityIds);
    
    static Response fetchEntities(final Client client, final List<EntityId> entityIds) {
    	return client.request(target -> target.path(Naming.Query.Client.ENTITY), req -> req.accept(MediaType.APPLICATION_JSON))
    			.method(HttpMethod.PATCH, Entity.json(entityIds));
    }
	
	@GET
    @Path("query/{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
	Response executeQuery(
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
            ) throws Exception;
    
    static <T extends Object> List<T> executeQuery(
    		final Client client,
    		final String schema,
    		final GenericType<List<T>> type,
    		final Function<WebTarget, WebTarget> paths,
    		final Integer firstResult,
    		final Integer maxResults,
    		final String... sort
            ) {
    	return client.request(
    			target -> paths.apply(
    					target.path(Naming.QUERY).path(schema).queryParam(Naming.Query.Client.FIRST, firstResult).queryParam(Naming.Query.Client.MAX, maxResults).queryParam(Naming.Query.Client.SORT, (Object[])sort)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(type);
    }
    
    static Response executeQuery(
    		final Client client,
    		final String schema,
    		final Function<WebTarget, WebTarget> paths,
    		final Integer firstResult,
    		final Integer maxResults,
    		final String... sort){
    	return client.request(
    			target -> paths.apply(
    					target.path(Naming.QUERY).path(schema).queryParam(Naming.Query.Client.FIRST, firstResult).queryParam(Naming.Query.Client.MAX, maxResults).queryParam(Naming.Query.Client.SORT, (Object[])sort)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    }
    
    @HEAD
    @Path("query/{schema}/{criteria: .+}")
	Response executeCountQuery(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Query.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @Context
            final SecurityContext context
            ) throws Exception;
    
    static Integer executeCountQuery(
    		final Client client,
    		final String schema,
    		final Function<WebTarget, WebTarget> paths){
    	final String count = client.request(
    			target -> paths.apply(
    					target.path(Naming.QUERY).path(schema)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.head()
    			.getHeaderString(Naming.Query.Client.ENTITY_COUNT);
    	return Integer.parseInt(count);
    }
}
