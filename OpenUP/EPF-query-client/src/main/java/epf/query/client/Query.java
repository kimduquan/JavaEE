package epf.query.client;

import java.util.List;
import java.util.function.Function;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.QUERY)
public interface Query {
	
	/**
	 * @param tenant
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
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
	
	/**
	 * @param client
	 * @param cls
	 * @param schema
	 * @param entity
	 * @param entityId
	 */
	static <T> T getEntity(final Client client, final Class<T> cls, final String schema, final String entity, final String entityId) {
		return client.request(
    			target -> target.path(Naming.Query.Client.ENTITY).path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(cls);
	}
	
	/**
	 * @param client
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	static Response getEntity(final Client client, final String schema, final String entity, final String entityId) {
		return client.request(
    			target -> target.path(Naming.Query.Client.ENTITY).path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
				.get();
	}
	
	/**
	 * @param tenant
	 * @param schema
	 * @param entity
	 * @return
	 */
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
	
	/**
	 * @param client
	 * @param schema
	 * @param entity
	 * @return
	 */
	static Integer countEntity(final Client client, final String schema, final String entity) {
		final String count = client.request(
    			target -> target.path(Naming.Query.Client.ENTITY).path(schema).path(entity), 
    			req -> req
    			)
				.head()
				.getHeaderString(Naming.Query.Client.ENTITY_COUNT);
		return Integer.parseInt(count);
	}
	
	/**
	 * @param tenant
	 * @param entityIds
	 * @return
	 */
	@PATCH
    @Path(Naming.Query.Client.ENTITY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response fetchEntities(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		final List<EntityId> entityIds);
    
    /**
     * @param client
     * @param entityIds
     * @return
     */
    static Response fetchEntities(final Client client, final List<EntityId> entityIds) {
    	return client.request(target -> target.path(Naming.Query.Client.ENTITY), req -> req.accept(MediaType.APPLICATION_JSON))
    			.method(HttpMethod.PATCH, Entity.json(entityIds));
    }
	
	/**
	 * @param tenant
	 * @param schema
	 * @param paths
	 * @param firstResult
	 * @param maxResults
	 * @param context
	 * @param sort
	 */
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
    
    /**
     * @param client
     * @param schema
     * @param type
     * @param paths
     * @param firstResult
     * @param maxResults
     * @param sort
     */
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
    
    /**
     * @param client
     * @param schema
     * @param paths
     * @param firstResult
     * @param maxResults
     * @param sort
     */
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
    
    /**
     * @param tenant
     * @param schema
     * @param paths
     * @param context
     * @return
     */
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
    
    /**
     * @param client
     * @param schema
     * @param paths
     * @return
     */
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
