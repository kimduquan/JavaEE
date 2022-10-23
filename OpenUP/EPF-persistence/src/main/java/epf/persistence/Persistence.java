package epf.persistence;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Forget;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.naming.Naming;
import epf.persistence.internal.EntityTransaction;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.Request;
import epf.schema.utility.SchemaUtil;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@ApplicationScoped
public class Persistence implements epf.persistence.client.Entities {
	
	/**
	 *
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(Persistence.class.getName());
	
	/**
	 *
	 */
	private final transient Map<String, EntityTransaction> transactionStore = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient SchemaUtil schemaUtil;
    
    /**
     * 
     */
    @Inject
    transient Validator validator;
    
    /**
     * 
     */
    @Inject
    transient EntityManager manager;
    
    /**
     * 
     */
    @Inject
    transient Request request;
    
    /**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final List<Class<?>> entityClasses = manager.getMetamodel().getEntities().stream().map(entity -> entity.getJavaType()).collect(Collectors.toList());
		schemaUtil = new SchemaUtil(entityClasses);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		schemaUtil.clear();
	}
    
    @Override
    @Transactional
    @LRA(LRA.Type.NESTED)
    public Response persist(
    		final String tenant,
    		final String schema,
            final String name,
            final HttpHeaders headers,
            final InputStream body
            ) throws Exception{
    	request.setTenant(tenant);
    	request.setSchema(schema);
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name);
    	if(entityType.isEmpty()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entity = EntityUtil.toObject(entityType.get(), body);
    	if(!validator.validate(entity).isEmpty()) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
        }
    	
    	manager.persist(entity);
        manager.flush();
        final JsonPatch diff = JsonUtil.createDiff(new Object(), entity);
        
        final Object transactionEntity = entity;
        final PostPersist transactionEvent = new PostPersist();
        transactionEvent.setTenant(request.getTenant());
        transactionEvent.setSchema(request.getSchema());
        transactionEvent.setEntity(transactionEntity);
        final EntityTransaction transaction = new EntityTransaction(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER));
        transaction.setEvent(transactionEvent);
        transaction.setDiff(JsonUtil.toString(diff.toJsonArray()));
        
        final Optional<Field> entityIdField = schemaUtil.getEntityIdField(transactionEntity.getClass());
        final Object entityId = entityIdField.get().get(transactionEntity);
    	transaction.setEntityId(entityId);
    	
        transactionStore.put(transaction.getId(), transaction);
        LOGGER.info(String.format("put[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transaction.getId()));
        
    	return Response.ok(JsonUtil.toString(entity)).build();
    }
    
    @Override
    @Transactional
    @LRA(LRA.Type.NESTED)
	public Response merge(
    		final String tenant,
			final String schema,
			final String name, 
			final String id,
            final HttpHeaders headers,
			final InputStream body
			) throws Exception {
    	request.setTenant(tenant);
    	request.setSchema(schema);
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name);
    	if(entityType.isEmpty()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entityId = EntityUtil.getEntityId(entityType.get(), id);
    	final Object entityObject = manager.find(entityType.get().getJavaType(), entityId);
    	if(entityObject == null) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entity = EntityUtil.toObject(entityType.get(), body);
    	if(!validator.validate(entity).isEmpty()) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final JsonObject preEntity = JsonUtil.toJson(entityObject);
        
    	final Object transactionEntity = entityObject;
        final PostUpdate transactionEvent = new PostUpdate();
        transactionEvent.setTenant(request.getTenant());
        transactionEvent.setSchema(request.getSchema());
        transactionEvent.setEntity(transactionEntity);
        final EntityTransaction transaction = new EntityTransaction(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER));
        transaction.setEvent(transactionEvent);
        transaction.setEntityId(entityId);
        
        final Object mergedEntity = manager.merge(entity);
        manager.flush();
        
        final JsonObject postEntity = JsonUtil.toJson(mergedEntity);
        final JsonPatch diff = Json.createDiff(preEntity, postEntity);
        transaction.setDiff(JsonUtil.toString(diff.toJsonArray()));
        
        transactionStore.put(transaction.getId(), transaction);
        LOGGER.info(String.format("put[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transaction.getId()));
        
        
    	return Response.ok(JsonUtil.toString(mergedEntity)).build();
	}
    
    @Override
    @Transactional
    @LRA(LRA.Type.NESTED)
    public Response remove(
    		final String tenant,
    		final String schema,
    		final String name,
    		final String id,
            final HttpHeaders headers
            ) throws Exception {
    	request.setTenant(tenant);
    	request.setSchema(schema);
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name);
    	if(entityType.isEmpty()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entityId = EntityUtil.getEntityId(entityType.get(), id);
    	final Object entityObject = manager.find(entityType.get().getJavaType(), entityId);
    	if(entityObject == null) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	
    	final JsonPatch diff = JsonUtil.createDiff(entityObject, new Object());
    	
    	manager.remove(entityObject);
    	manager.flush();
    	
    	final Object transactionEntity = entityObject;
        final PostRemove transactionEvent = new PostRemove();
        transactionEvent.setTenant(request.getTenant());
        transactionEvent.setSchema(request.getSchema());
        transactionEvent.setEntity(transactionEntity);
        final EntityTransaction transaction = new EntityTransaction(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER));
        transaction.setEvent(transactionEvent);
        transaction.setEntityId(entityId);
        transaction.setDiff(JsonUtil.toString(diff.toJsonArray()));
        
        transactionStore.put(transaction.getId(), transaction);
        LOGGER.info(String.format("put[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transaction.getId()));
        
    	return Response.ok().build();
    }
    
    /**
     * @return
     * @throws Exception 
     */
    @Compensate
    @Path(Naming.TRANSACTION)
    @PUT
    @Transactional
    public Response rollback(
    		@Context
    		final HttpHeaders headers) throws Exception {
    	final String transactionId = headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER);
    	final EntityTransaction transaction = transactionStore.remove(transactionId);
		if(transaction != null) {
    		final EntityEvent transactionEvent = transaction.getEvent();
			request.setTenant(transactionEvent.getTenant());
			request.setSchema(transactionEvent.getSchema());
    		if(transactionEvent instanceof PostPersist) {
     			final Object entity = manager.find(transactionEvent.getEntity().getClass(), transaction.getEntityId());
    			if(entity != null) {
    				manager.remove(entity);
    				manager.flush();
    			}
    		}
    		else if(transactionEvent instanceof PostUpdate) {
    			final Object entity = manager.find(transactionEvent.getEntity().getClass(), transaction.getEntityId());
    			if(entity != null) {
    				manager.merge(transactionEvent.getEntity());
    				manager.flush();
    			}
    		}
     		else if(transactionEvent instanceof PostRemove) {
    			manager.persist(transactionEvent.getEntity());
    			manager.flush();
    		}
            LOGGER.info(String.format("rollback[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transaction.getId()));
		}
    	return Response.ok(ParticipantStatus.Compensated.name()).build();
    }
    
    /**
     * @param headers
     * @return
     */
    @Forget
    @Path(Naming.Transaction.TRANSACTION_ACTIVE)
    @PUT
    public Response commit(
    		@Context
    		final HttpHeaders headers) {
    	final String transactionId = headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER);
    	transactionStore.remove(transactionId);
        LOGGER.info(String.format("commit[%s]id=%s", headers.getHeaderString(HttpHeaders.HOST), transactionId));
    	return Response.ok(ParticipantStatus.Completed.name()).build();
    }
}
