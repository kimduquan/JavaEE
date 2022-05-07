package epf.schema.utility;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.persistence.Id;

/**
 * @author PC
 *
 */
public class SchemaUtil {

	/**
	 * 
	 */
	private transient final Map<String, Optional<Field>> entityIdFields = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Optional<String>> entityNames = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Optional<String>> entitySchemas = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Class<?>> entityClasses = new ConcurrentHashMap<>();
	
	/**
	 * @param cls
	 * @return
	 */
	private Optional<Field> computeEntityIdField(final Class<?> cls) {
		final Optional<Field> idField = Stream.of(cls.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findAny();
		idField.ifPresent(f -> f.setAccessible(true));
		return idField;
	}
	
	/**
	 * @param cls
	 * @return
	 */
	private Optional<String> computeEntityName(final Class<?> cls) {
		Optional<String> name = Optional.empty();
		if(cls.isAnnotationPresent(javax.persistence.Entity.class)) {
			name = Optional.of(((javax.persistence.Entity)cls.getAnnotation(javax.persistence.Entity.class)).name());
		}
		return name;
	}
	
	/**
	 * @param cls
	 * @return
	 */
	private Optional<String> computeEntitySchema(final Class<?> cls){
		Optional<String> name = Optional.empty();
		if(cls.isAnnotationPresent(javax.persistence.Entity.class)) {
			name = Optional.of(((javax.persistence.Table)cls.getAnnotation(javax.persistence.Table.class)).schema());
		}
		return name;
	}
	
	/**
	 * @param entityClasses
	 */
	public SchemaUtil(final List<Class<?>> entityClasses) {
		entityClasses.forEach(entityClass -> entitySchemas.put(entityClass.getName(), computeEntitySchema(entityClass)));
		entityClasses.forEach(entityClass -> {
			final Optional<String> entityName = computeEntityName(entityClass);
			entityNames.put(entityClass.getName(), entityName);
			if(entityName.isPresent()) {
				this.entityClasses.put(entityName.get(), entityClass);
			}
		});
		entityClasses.forEach(entityClass -> entityIdFields.put(entityClass.getName(), computeEntityIdField(entityClass)));
	}
	
	/**
	 * 
	 */
	public void clear() {
		entityIdFields.clear();
		entityClasses.clear();
		entitySchemas.clear();
		entityNames.clear();
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public Optional<Field> getEntityIdField(final Class<?> cls) {
		return entityIdFields.computeIfAbsent(cls.getName(), name -> computeEntityIdField(cls));
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public Optional<String> getEntityName(final Class<?> cls) {
		return entityNames.computeIfAbsent(cls.getName(), name -> {
			final Optional<String> entityName = computeEntityName(cls);
			if(entityName.isPresent()) {
				entityClasses.putIfAbsent(entityName.get(), cls);
			}
			return entityName;
		});
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public Optional<String> getEntitySchema(final Class<?> cls){
		return entitySchemas.computeIfAbsent(cls.getName(), key -> computeEntitySchema(cls));
	}
	
	/**
	 * @param entityName
	 * @return
	 */
	public Optional<Class<?>> getEntityClass(final String entityName){
		return Optional.ofNullable(entityClasses.get(entityName));
	}
}
