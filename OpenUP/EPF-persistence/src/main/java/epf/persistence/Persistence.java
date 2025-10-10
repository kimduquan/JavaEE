package epf.persistence;

import java.io.InputStream;
import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonPatch;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Forget;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.management.util.OrganizationUtil;
import epf.naming.Naming;
import epf.persistence.cache.TransactionCache;
import epf.persistence.event.TransactionEvent;
import epf.persistence.event.TransactionEventType;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.EntityTransaction;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path(Naming.PERSISTENCE)
@ApplicationScoped
public class Persistence {
	
	private transient final static Logger LOGGER = LogManager.getLogger(Persistence.class.getName());
	
	@Inject
	transient TransactionCache cache;
    
    @Inject
    transient Validator validator;
    
    @Inject
    transient EntityManager manager;
    
    @POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @LRA(LRA.Type.NESTED)
    @RunOnVirtualThread
    public Response persist(
    		@PathParam(Naming.Persistence.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            @NotBlank
            final String name,
            @Context
            final HttpHeaders headers,
            @Context 
            final JsonWebToken jwt,
            @NotNull
            final InputStream body
            ) throws Exception {
    	final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, name);
    	if(!entityType.isPresent()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	Object entity = null;
    	try {
        	entity = JsonUtil.fromJson(body, entityType.get().getJavaType());
    	}
    	catch(Exception ex) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	if(!validator.validate(entity).isEmpty()) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
        }
    	
    	manager.persist(entity);
        manager.flush();
        
        final JsonPatch diff = JsonUtil.createDiff(new Object(), entity);
        
        final Object entityId = EntityUtil.getEntityId(entityType.get(), entity);
        
        final PostPersist entityEvent = new PostPersist();
        entityEvent.setTime(Instant.now().toEpochMilli());
        entityEvent.setId(entityId.toString());
        entityEvent.setEntity(entity);
        entityEvent.setName(entityType.get().getName());
        entityEvent.setSchema(entitySchema.get());
        entityEvent.setOrganization(organizationId);
        
        final EntityTransaction transaction = new EntityTransaction();
        transaction.setId(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER));
        transaction.setEvent(entityEvent);
        transaction.setDiff(JsonUtil.toString(diff.toJsonArray()));
    	
        cache.put(transaction);
        
        final String entityUri = "/" + Naming.PERSISTENCE + "/" + entitySchema.get() + "/" + entityType.get().getName() + "/" + entityId;
        return Response.created(URI.create(entityUri)).entity(JsonUtil.toString(entity)).build();
    }
    
    @PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @LRA(LRA.Type.NESTED)
    @RunOnVirtualThread
    public Response merge(
    		@PathParam(Naming.Persistence.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            @NotBlank
            final String name,
            @PathParam(Naming.Persistence.Client.ID)
            @NotBlank
            final String id,
            @Context
            final HttpHeaders headers,
            @Context 
            final JsonWebToken jwt,
            @NotNull
            final InputStream body
            ) throws Exception {
    	final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, name);
    	if(!entityType.isPresent()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	Object entityId = null;
    	try {
    		entityId = EntityUtil.convertEntityId(entityType.get(), id);
    	}
    	catch(NumberFormatException ex) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	final Object entityObject = manager.find(entityType.get().getJavaType(), entityId);
    	if(entityObject == null) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	Object entity = null;
    	try {
        	entity = JsonUtil.fromJson(body, entityType.get().getJavaType());
    	}
    	catch(Exception ex) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	if(!validator.validate(entity).isEmpty()) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final JsonObject preEntity = JsonUtil.toJsonObject(entityObject);
        
    	final PostUpdate entityEvent = new PostUpdate();
        entityEvent.setTime(Instant.now().toEpochMilli());
        entityEvent.setId(entityId.toString());
        entityEvent.setEntity(entityObject);
        entityEvent.setName(entityType.get().getName());
        entityEvent.setSchema(entitySchema.get());
        entityEvent.setOrganization(organizationId);
        
        final EntityTransaction transaction = new EntityTransaction();
        transaction.setId(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER));
        transaction.setEvent(entityEvent);
        
        final Object mergedEntity = manager.merge(entity);
        manager.flush();
        
        final JsonObject postEntity = JsonUtil.toJsonObject(mergedEntity);
        final JsonPatch diff = Json.createDiff(preEntity, postEntity);
        transaction.setDiff(JsonUtil.toString(diff.toJsonArray()));
        
        cache.put(transaction);
        
        return Response.ok(JsonUtil.toString(mergedEntity)).build();
	}
    
    @DELETE
    @Path("{schema}/{entity}/{id}")
    @Transactional
    @LRA(LRA.Type.NESTED)
    @RunOnVirtualThread
    public Response remove(
    		@PathParam(Naming.Persistence.Client.SCHEMA)
            @NotBlank
            final String schema,
            @PathParam(Naming.Persistence.Client.ENTITY)
            @NotBlank
            final String name,
            @PathParam(Naming.Persistence.Client.ID)
            @NotBlank
            final String id,
            @Context
            final HttpHeaders headers,
            @Context 
            final JsonWebToken jwt
            ) throws Exception {
    	final String organizationId = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, name);
    	if(!entityType.isPresent()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	Object entityId = null;
    	try {
    		entityId = EntityUtil.convertEntityId(entityType.get(), id);
    	}
    	catch(NumberFormatException ex) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	final Object entityObject = manager.find(entityType.get().getJavaType(), entityId);
    	if(entityObject == null) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	
    	final JsonObject preEntity = JsonUtil.toJsonObject(entityObject);
    	final JsonObject postEntity = JsonValue.EMPTY_JSON_OBJECT;
    	final JsonPatch diff = Json.createDiff(preEntity, postEntity);
    	
    	manager.remove(entityObject);
    	manager.flush();
    	
    	final PostRemove entityEvent = new PostRemove();
        entityEvent.setTime(Instant.now().toEpochMilli());
        entityEvent.setId(entityId.toString());
        entityEvent.setEntity(entityObject);
        entityEvent.setName(entityType.get().getName());
        entityEvent.setSchema(entitySchema.get());
        entityEvent.setOrganization(organizationId);
        
        final EntityTransaction transaction = new EntityTransaction();
        transaction.setId(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER));
        transaction.setEvent(entityEvent);
        transaction.setDiff(JsonUtil.toString(diff.toJsonArray()));
        
        cache.put(transaction);
        
    	return Response.ok().build();
    }
    
    @Compensate
    @Path(Naming.TRANSACTION)
    @PUT
    @Transactional
    @RunOnVirtualThread
    public Response rollback(
    		@Context
    		final HttpHeaders headers) throws Exception {
    	final String transactionId = headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER);
    	final EntityTransaction transaction = cache.remove(transactionId);
		if(transaction != null) {
	    	final TransactionEvent transactionEvent = new TransactionEvent();
	    	transactionEvent.setEventType(TransactionEventType.rollback);
	    	transactionEvent.setTransaction(transaction);
    		final EntityEvent entityEvent = transaction.getEvent();
    		final EntityType<?> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), entityEvent.getSchema(), entityEvent.getName()).get();
    		final Object entityId = EntityUtil.convertEntityId(entityType, entityEvent.getId());
    		if(entityEvent instanceof PostPersist) {
     			final Object entity = manager.find(entityEvent.getEntity().getClass(), entityId);
    			if(entity != null) {
    				manager.remove(entity);
    				manager.flush();
    			}
    		}
    		else if(entityEvent instanceof PostUpdate) {
    			final Object entity = manager.find(entityEvent.getEntity().getClass(), entityId);
    			if(entity != null) {
    				manager.merge(entityEvent.getEntity());
    				manager.flush();
    			}
    		}
     		else if(entityEvent instanceof PostRemove) {
    			manager.persist(entityEvent.getEntity());
    			manager.flush();
    		}
            LOGGER.info(String.format("rollback[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transaction.getId()));
		}
    	return Response.ok(ParticipantStatus.Compensated.name()).build();
    }
    
    @Forget
    @Path(Naming.TRANSACTION_ACTIVE)
    @PUT
    @Transactional
    @RunOnVirtualThread
    public Response commit(
    		@Context
    		final HttpHeaders headers) throws Exception {
    	final String transactionId = headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER);
    	final EntityTransaction transaction = cache.remove(transactionId);
    	final TransactionEvent transactionEvent = new TransactionEvent();
    	transactionEvent.setEventType(TransactionEventType.commit);
    	transactionEvent.setTransaction(transaction);
        LOGGER.info(String.format("commit[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transactionId));
    	return Response.ok(ParticipantStatus.Completed.name()).build();
    }
}
