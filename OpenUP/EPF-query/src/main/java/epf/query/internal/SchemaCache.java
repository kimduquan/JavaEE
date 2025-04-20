package epf.query.internal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.query.client.EntityId;
import epf.schema.utility.SchemaUtil;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class SchemaCache implements HealthCheck {
	
	private transient final static Logger LOGGER = LogManager.getLogger(SchemaCache.class.getName());

	private transient SchemaUtil schemaUtil;
	
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager entityManager;
	
	@PostConstruct
	protected void postConstruct() {
		final List<Class<?>> entityClasses = entityManager.getMetamodel().getEntities().stream().map(entity -> entity.getJavaType()).collect(Collectors.toList());
		schemaUtil = new SchemaUtil(entityClasses);
	}
	
	@PreDestroy
	protected void preDestroy() {
		schemaUtil.clear();
	}
	
	public Optional<EntityKey> getKey(final Object entity) {
		final Class<?> cls = entity.getClass();
		final Optional<String> entitySchema = schemaUtil.getEntitySchema(cls);
		final Optional<String> entityName = schemaUtil.getEntityName(cls);
		final Optional<Field> idField = schemaUtil.getEntityIdField(cls);
		Optional<Object> entityId = Optional.empty();
		if(entitySchema.isPresent() && entityName.isPresent() && idField.isPresent()) {
			try {
				entityId = Optional.ofNullable(idField.get().get(entity));
			} 
			catch (IllegalAccessException | IllegalArgumentException e) {
				LOGGER.log(Level.SEVERE, cls.getName() + "." + idField.get().getName(), e);
			}
		}
		Optional<EntityKey> key = Optional.empty();
		if(entitySchema.isPresent() && entityName.isPresent() && entityId.isPresent()) {
			key = Optional.of(new EntityKey(entitySchema.get(), entityName.get(), entityId.get()));
		}
		return key;
	}
	
	public Optional<QueryKey> getQueryKey(final Class<?> entityCls) {
		final Optional<String> entitySchema = schemaUtil.getEntitySchema(entityCls);
		final Optional<String> entityName = schemaUtil.getEntityName(entityCls);
		if(entitySchema.isPresent() && entityName.isPresent()) {
			return Optional.of(new QueryKey(entitySchema.get(), entityName.get()));
		}
		return Optional.empty();
	}
	
	public EntityKey getKey(final String schema, final String entityName, final Object entityId) {
		return new EntityKey(schema, entityName, entityId);
	}
	
	public EntityKey getSearchKey(final EntityId entityId) {
		return new EntityKey(entityId.getSchema(), entityId.getName(), entityId.getAttributes().values().iterator().next());
	}
	
	public QueryKey getQueryKey(
    		final String schema, 
    		final String entityName) {
		return new QueryKey(schema, entityName);
	}
	
	public Optional<Object> getEntityId(final Object entity) throws Exception{
		final Class<?> cls = entity.getClass();
		final Optional<Field> idField = schemaUtil.getEntityIdField(cls);
		Optional<Object> entityId = Optional.empty();
		if(idField.isPresent()) {
			return Optional.ofNullable(idField.get().get(entity));
		}
		return entityId;
	}
	
	public Optional<String> getEntityName(final String table){
		return schemaUtil.getEntityName(table);
	}
	
	public Optional<Class<?>> getEntityClass(final String entity) {
		return Optional.ofNullable(schemaUtil.getEntityClass(entity));
	}
	
	public Optional<String> getEntitySchema(final Class<?> cls){
		return schemaUtil.getEntitySchema(cls);
	}
	
	public Optional<String> getEntityIdFieldName(final String entity){
		Optional<String> entityIdFieldName = Optional.empty();
		final Optional<Class<?>> entityClass = Optional.ofNullable(schemaUtil.getEntityClass(entity));
		if(entityClass.isPresent()) {
			final Optional<Field> entityIdField = schemaUtil.getEntityIdField(entityClass.get());
			if(entityIdField.isPresent()) {
				entityIdFieldName = Optional.of(entityIdField.get().getName());
			}
		}
		return entityIdFieldName;
	}
	
	public Map<String, String> getColumnFields(final String entity){
		return schemaUtil.getEntityColumnFields(entity);
	}
	
	public Optional<String> getEntityIdFieldType(final String entity){
		Optional<String> entityIdFieldType = Optional.empty();
		final Optional<Class<?>> entityClass = Optional.ofNullable(schemaUtil.getEntityClass(entity));
		if(entityClass.isPresent()) {
			final Optional<Field> entityIdField = schemaUtil.getEntityIdField(entityClass.get());
			if(entityIdField.isPresent()) {
				entityIdFieldType = Optional.of(entityIdField.get().getType().getName());
			}
		}
		return entityIdFieldType;
	}

	@Override
	public HealthCheckResponse call() {
		if(!entityManager.isOpen()) {
			return HealthCheckResponse.down("epf-query-schema-cache");
		}
		return HealthCheckResponse.up("epf-query-schema-cache");
	}
}
