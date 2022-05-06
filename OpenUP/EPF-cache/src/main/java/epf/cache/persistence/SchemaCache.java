package epf.cache.persistence;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
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
	private final transient SchemaUtil schemaUtil = new SchemaUtil();
	
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
			final QueryKey queryKey = new QueryKey(entitySchema.get(), entityName.get());
			return Optional.of(queryKey);
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
	 * @param key
	 * @return
	 */
	public EntityKey parseKey(final String key) {
		return EntityKey.valueOf(key);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Optional<QueryKey> parseQueryKey(final String key) {
		return Optional.ofNullable(QueryKey.valueOf(key));
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
}
