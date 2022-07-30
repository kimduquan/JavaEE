package epf.persistence;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.SchemaUtil;
import epf.util.json.JsonUtil;

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
    		final String schema,
            final String name,
            final HttpHeaders headers,
            final InputStream body
            ) throws Exception{
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
        final String jsonEntity = JsonUtil.toString(entity);
        
        final Object transactionEntity = entity;
        final PostPersist transactionEvent = new PostPersist();
        transactionEvent.setEntity(transactionEntity);
        final EntityTransaction transaction = new EntityTransaction();
        transaction.setEvent(transactionEvent);
        
        final Optional<Field> entityIdField = schemaUtil.getEntityIdField(transactionEntity.getClass());
        final Object entityId = entityIdField.get().get(transactionEntity);
    	transaction.setEntityId(entityId);
    	
        transactionStore.put(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER), transaction);
        
    	return Response.ok(jsonEntity).build();
    }
    
    @Override
    @Transactional
    @LRA(LRA.Type.NESTED)
	public Response merge(
			final String schema,
			final String name, 
			final String id,
            final HttpHeaders headers,
			final InputStream body
			) throws Exception {
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

        JsonUtil.toString(entityObject);
        
    	final Object transactionEntity = entityObject;
        final PostUpdate transactionEvent = new PostUpdate();
        transactionEvent.setEntity(transactionEntity);
        final EntityTransaction transaction = new EntityTransaction();
        transaction.setEvent(transactionEvent);
        transaction.setEntityId(entityId);
        
        final Object mergedEntity = manager.merge(entity);
        final String jsonEntity = JsonUtil.toString(mergedEntity);
        
        transactionStore.put(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER), transaction);
        
    	return Response.ok(jsonEntity).build();
	}
    
    @Override
    @Transactional
    @LRA(LRA.Type.NESTED)
    public Response remove(
    		final String schema,
    		final String name,
    		final String id,
            final HttpHeaders headers
            ) throws Exception {
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
    	
    	JsonUtil.toString(entityObject);
    	manager.remove(entityObject);
    	
    	final Object transactionEntity = entityObject;
        final PostRemove transactionEvent = new PostRemove();
        transactionEvent.setEntity(transactionEntity);
        final EntityTransaction transaction = new EntityTransaction();
        transaction.setEvent(transactionEvent);
        transaction.setEntityId(entityId);
        transactionStore.put(headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER), transaction);
        
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
    		if(transactionEvent instanceof PostPersist) {
     			final Object entity = manager.find(transactionEvent.getEntity().getClass(), transaction.getEntityId());
    			if(entity != null) {
    				manager.remove(entity);
    			}
    		}
    		else if(transactionEvent instanceof PostUpdate) {
    			final Object entity = manager.find(transactionEvent.getEntity().getClass(), transaction.getEntityId());
    			if(entity != null) {
    				manager.merge(transactionEvent.getEntity());
    			}
    		}
     		else if(transactionEvent instanceof PostRemove) {
    			manager.persist(transactionEvent.getEntity());
    		}
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
    	return Response.ok(ParticipantStatus.Completed.name()).build();
    }
}
