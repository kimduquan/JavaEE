package epf.cache.persistence;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.cache.Cache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.SchemaUtil;
import epf.util.StringUtil;
import epf.util.concurrent.ObjectQueue;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class EntityCache extends ObjectQueue<EntityEvent> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EntityCache.class.getName());
	
	/**
	 * 
	 */
	private transient final SchemaUtil schemaUtil = new SchemaUtil();
	
	/**
	 * 
	 */
	private transient final Cache<String, Object> cache;
	
	/**
	 * @param cache
	 */
	public EntityCache(final Cache<String, Object> cache) {
		this.cache = cache;
	}
	
	/**
	 * @param entity
	 * @return
	 */
	protected Optional<String> getKey(final Object entity) {
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
		Optional<String> key = Optional.empty();
		if(entitySchema.isPresent() && entityName.isPresent() && entityId.isPresent()) {
			key = Optional.of(StringUtil.join(entitySchema.get(), entityName.get(), String.valueOf(entityId.get())));
		}
		return key;
	}

	@Override
	public void accept(final EntityEvent event) {
		final Optional<String> key = getKey(event.getEntity());
		if(key.isPresent()) {
			if(event instanceof PostUpdate) {
				cache.replace(key.get(), event.getEntity());
			}
			else if(event instanceof PostPersist) {
				cache.put(key.get(), event.getEntity());
			}
			else if(event instanceof PostRemove) {
				cache.remove(key.get());
			}
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
			final String schema,
            final String name,
            final String entityId
            ) {
		final String key = StringUtil.join(schema, name, entityId);
		Optional<Object> entity = Optional.empty();
		if(cache.containsKey(key)) {
			entity = Optional.ofNullable(cache.get(key));
		}
		return entity;
	}
}
