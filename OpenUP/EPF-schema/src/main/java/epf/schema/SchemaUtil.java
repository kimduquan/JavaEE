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
	private transient final Map<String, Field> entityIdFields = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, String> entityNames = new ConcurrentHashMap<>();
	
	/**
	 * @param cls
	 * @return
	 */
	public Field getEntityIdField(final Class<?> cls) {
		return entityIdFields.computeIfAbsent(cls.getName(), name -> {
			final Optional<Field> idField = Stream.of(cls.getDeclaredFields())
					.filter(field -> field.isAnnotationPresent(Id.class))
					.findAny();
			idField.ifPresent(f -> f.setAccessible(true));
			return idField.orElse(null);
		});
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public String getEntityName(final Class<?> cls) {
		return entityNames.computeIfAbsent(cls.getName(), key -> {
			String name = null;
			if(cls.isAnnotationPresent(javax.persistence.Entity.class)) {
				name = ((javax.persistence.Entity)cls.getAnnotation(javax.persistence.Entity.class)).name();
			}
			return name;
		});
	}
}
