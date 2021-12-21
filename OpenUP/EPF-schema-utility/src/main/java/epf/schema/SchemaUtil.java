/**
 * 
 */
package epf.schema;

import java.lang.reflect.Field;
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
	 * @param cls
	 * @return
	 */
	public Optional<Field> getEntityIdField(final Class<?> cls) {
		return entityIdFields.computeIfAbsent(cls.getName(), name -> {
			final Optional<Field> idField = Stream.of(cls.getDeclaredFields())
					.filter(field -> field.isAnnotationPresent(Id.class))
					.findAny();
			idField.ifPresent(f -> f.setAccessible(true));
			return idField;
		});
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public Optional<String> getEntityName(final Class<?> cls) {
		return entityNames.computeIfAbsent(cls.getName(), key -> {
			Optional<String> name = Optional.empty();
			if(cls.isAnnotationPresent(javax.persistence.Entity.class)) {
				name = Optional.of(((javax.persistence.Entity)cls.getAnnotation(javax.persistence.Entity.class)).name());
			}
			return name;
		});
	}
}
