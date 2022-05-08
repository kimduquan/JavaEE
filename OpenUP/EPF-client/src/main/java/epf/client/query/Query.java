package epf.client.query;

import java.util.List;
import java.util.function.Function;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	 *
	 */
	String ENTITY = "entity";
	
	/**
	 * 
	 */
	String FIRST = "first";
	/**
	 * 
	 */
	String MAX = "max";
	
	/**
	 *
	 */
	String SORT = "sort";
	
	/**
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	@GET
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
    		@PathParam("schema")
            @NotNull
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotNull
            @NotBlank
            final String entity,
            @PathParam("id")
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
    			target -> target.path(ENTITY).path(schema).path(entity).path(entityId), 
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
    			target -> target.path(ENTITY).path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
				.get();
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @return
	 */
	@HEAD
	@Path("query/{schema}/{entity}")
    Response countEntity(
    		@PathParam("schema")
            @NotNull
            @NotBlank
            final String schema,
            @PathParam("entity")
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
    			target -> target.path(Naming.QUERY).path(schema).path(entity), 
    			req -> req
    			)
				.head()
				.getHeaderString(Naming.Query.ENTITY_COUNT);
		return Integer.parseInt(count);
	}
	
	/**
	 * @param schema
	 * @param paths
	 * @param firstResult
	 * @param maxResults
	 * @param context
	 * @return
	 */
	@GET
    @Path("query/{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
	Response executeQuery(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths,
            @QueryParam(FIRST)
            final Integer firstResult,
            @QueryParam(MAX)
            final Integer maxResults,
            @Context
            final SecurityContext context,
            @QueryParam(SORT)
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
    					target.path(Naming.QUERY).path(schema).queryParam(FIRST, firstResult).queryParam(MAX, maxResults).queryParam(SORT, (Object[])sort)
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
    					target.path(Naming.QUERY).path(schema).queryParam(FIRST, firstResult).queryParam(MAX, maxResults).queryParam(SORT, (Object[])sort)
    					), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    }
    
    /**
     * @param schema
     * @param paths
     * @param context
     * @return
     * @throws Exception
     */
    @HEAD
    @Path("query/{schema}/{criteria: .+}")
	Response executeCountQuery(
    		@PathParam("schema")
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
    			.getHeaderString(Naming.Query.ENTITY_COUNT);
    	return Integer.parseInt(count);
    }
}
