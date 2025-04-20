package epf.persistence.client;

import java.io.InputStream;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.PERSISTENCE)
public interface Entities {
    
    @POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response persist(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Persistence.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            @NotBlank
            final String entity,
            @Context
            final HttpHeaders headers,
            @NotNull
            final InputStream body
            ) throws Exception;
    
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
    
    @PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response merge(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Persistence.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            @NotBlank
            final String entity,
            @PathParam(Naming.Persistence.Client.ID)
            @NotBlank
            final String entityId,
            @Context
            final HttpHeaders headers,
            @NotNull
            final InputStream body
            ) throws Exception;
    
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
    
    @DELETE
    @Path("{schema}/{entity}/{id}")
    Response remove(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(Naming.Persistence.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            @NotBlank
            final String entity,
            @PathParam(Naming.Persistence.Client.ID)
            @NotBlank
            final String entityId,
            @Context
            final HttpHeaders headers
            ) throws Exception;
    
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
