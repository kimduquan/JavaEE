package epf.cache.persistence;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import epf.schema.utility.SchemaUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SchemaCache {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(SchemaCache.class.getName());

	/**
	 * 
	 */
	private transient SchemaUtil schemaUtil;
	
	/**
	 *
	 */
	@PersistenceContext(unitName = "EPF_Cache")
	private transient EntityManager entityManager;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final Stream<Class<?>> entityClasses = entityManager.getMetamodel().getEntities().stream().map(entity -> entity.getBindableJavaType());
		schemaUtil = new SchemaUtil(entityClasses);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		schemaUtil.clear();
	}
	
	/**
	 * @param entity
	 * @return
	 */
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
			key = Optional.of(new EntityKey(entitySchema.get(), entityName.get(), String.valueOf(entityId.get())));
		}
		return key;
	}
	
	/**
	 * @param entityCls
	 * @return
	 */
	public Optional<QueryKey> getQueryKey(final Class<?> entityCls) {
		final Optional<String> entitySchema = schemaUtil.getEntitySchema(entityCls);
		final Optional<String> entityName = schemaUtil.getEntityName(entityCls);
		if(entitySchema.isPresent() && entityName.isPresent()) {
			return Optional.of(new QueryKey(entitySchema.get(), entityName.get()));
		}
		return Optional.empty();
	}
	
	/**
	 * @param schema
	 * @param entityName
	 * @param entityId
	 * @return
	 */
	public EntityKey getKey(final String schema, final String entityName, final Object entityId) {
		return new EntityKey(schema, entityName, entityId);
	}
	
	/**
	 * @param schema
	 * @param entityName
	 * @return
	 */
	public QueryKey getQueryKey(final String schema, final String entityName) {
		return new QueryKey(schema, entityName);
	}
	
	/**
	 * @param entityName
	 * @return
	 */
	public Optional<Class<?>> getEntityClass(final String schema, final String entityName){
		return schemaUtil.getEntityClass(entityName);
	}
	
	/**
	 * @param entity
	 * @return
	 * @throws Exception  
	 */
	public Optional<Object> getEntityId(final Object entity) throws Exception{
		final Class<?> cls = entity.getClass();
		final Optional<Field> idField = schemaUtil.getEntityIdField(cls);
		Optional<Object> entityId = Optional.empty();
		if(idField.isPresent()) {
			return Optional.ofNullable(idField.get().get(entity));
		}
		return entityId;
	}

	public SchemaUtil getSchemaUtil() {
		return schemaUtil;
	}
}
