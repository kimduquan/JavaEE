package epf.persistence.client;

import java.io.InputStream;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
public interface Entities {
	
	/**
	 * 
	 */
	String SCHEMA = "schema";
	
	/**
	 * 
	 */
	String ENTITY = "entity";
	
	/**
	 * 
	 */
	String ID = "id";
    
    /**
     * @param schema
     * @param entity
     * @param body
     * @return
     * @throws Exception 
     */
    @POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response persist(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(ENTITY)
            @NotBlank
            final String entity,
            @Context
            final HttpHeaders headers,
            @NotNull
            final InputStream body
            ) throws Exception;
    
    /**
     * @param client
     * @param cls
     * @param schema
     * @param entity
     * @param body
     */
    static <T> T persist(
    		final Client client,
    		final Class<T> cls,
    		final String schema,
    		final String entity,
    		final T body
            ){
    	return client.request(
    			target -> target.path(schema).path(entity), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(body), cls);
    }
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param body
     */
    static Response persist(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String body
            ){
    	return client.request(
    			target -> target.path(schema).path(entity), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.entity(body, MediaType.APPLICATION_JSON));
    }
    
    /**
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     * @throws Exception 
     */
    @PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response merge(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(ENTITY)
            @NotBlank
            final String entity,
            @PathParam(ID)
            @NotBlank
            final String entityId,
            @Context
            final HttpHeaders headers,
            @NotNull
            final InputStream body
            ) throws Exception;
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String entityId,
    		final Object body
            ) {
    	client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req
    			)
    	.put(Entity.json(body));
    }
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String entityId,
    		final String body
            ) {
    	client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req
    			)
    	.put(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));
    }
    
    /**
     * @param schema
     * @param entity
     * @param entityId
     */
    @DELETE
    @Path("{schema}/{entity}/{id}")
    Response remove(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(ENTITY)
            @NotBlank
            final String entity,
            @PathParam(ID)
            @NotBlank
            final String entityId,
            @Context
            final HttpHeaders headers
            ) throws Exception;
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     */
    static void remove(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String entityId
            ) {
    	client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req
    			)
    	.delete();
    }
}
