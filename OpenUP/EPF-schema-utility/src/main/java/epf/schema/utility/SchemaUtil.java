package epf.schema.utility;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	 *
	 */
	private transient final Map<String, String> entityTables = new ConcurrentHashMap<>();
	
	/**
	 *
	 */
	private transient final Map<String, Map<String, String>> entityColumnFields = new ConcurrentHashMap<>();
	
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
		if(cls.isAnnotationPresent(Entity.class)) {
			name = Optional.of(((Entity)cls.getAnnotation(Entity.class)).name());
		}
		return name;
	}
	
	/**
	 * @param cls
	 * @return
	 */
	private Optional<String> computeEntitySchema(final Class<?> cls){
		Optional<String> name = Optional.empty();
		if(cls.isAnnotationPresent(Entity.class)) {
			name = Optional.of(((Table)cls.getAnnotation(Table.class)).schema());
		}
		return name;
	}
	
	/**
	 * @param cls
	 * @return
	 */
	private Optional<Table> computeEntityTable(final Class<?> cls) {
		Optional<Table> table = Optional.empty();
		if(cls.isAnnotationPresent(Table.class)) {
			table = Optional.of(cls.getAnnotation(Table.class));
		}
		return table;
	}
	
	/**
	 * @param classes
	 */
	public SchemaUtil(final List<Class<?>> classes) {
		classes.forEach(entityClass -> entitySchemas.put(entityClass.getName(), computeEntitySchema(entityClass)));
		classes.forEach(entityClass -> {
			final Optional<String> entityName = computeEntityName(entityClass);
			if(entityName.isPresent()) {
				entityNames.put(entityClass.getName(), entityName);
				entityClasses.put(entityName.get(), entityClass);
				final Optional<Table> entityTable = computeEntityTable(entityClass);
				if(entityTable.isPresent()) {
					entitySchemas.put(entityClass.getName(), Optional.of(entityTable.get().schema()));
					entityTables.put(entityTable.get().name(), entityName.get());
				}
				final Map<String, String> fields = new ConcurrentHashMap<>();
				for(Field field : entityClass.getDeclaredFields()) {
					if(field.isAnnotationPresent(Column.class)) {
						final Column column = field.getAnnotation(Column.class);
						fields.put(column.name(), field.getName());
					}
				}
				entityColumnFields.put(entityName.get(), fields);
			}
		});
		classes.forEach(entityClass -> entityIdFields.put(entityClass.getName(), computeEntityIdField(entityClass)));
	}
	
	/**
	 * 
	 */
	public void clear() {
		entityIdFields.clear();
		entityClasses.clear();
		entitySchemas.clear();
		entityNames.clear();
		entityTables.clear();
		entityColumnFields.clear();
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
	 * @param entityTable
	 * @return
	 */
	public Optional<String> getEntityName(final String entityTable){
		return Optional.ofNullable(entityTables.get(entityTable));
	}
	
	/**
	 * @param entityName
	 * @return
	 */
	public Map<String, String> getEntityColumnFields(final String entityName){
		return entityColumnFields.get(entityName);
	}
	
	/**
	 * @param entityName
	 * @return
	 */
	public Class<?> getEntityClass(final String entityName){
		return entityClasses.get(entityName);
	}
}
