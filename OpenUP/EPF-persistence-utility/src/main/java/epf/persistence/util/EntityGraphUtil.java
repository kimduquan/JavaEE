package epf.persistence.util;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedEntityGraph;

/**
 * @author PC
 *
 */
public interface EntityGraphUtil {

	/**
	 * @param entityClass
	 * @return
	 */
	static String getEntityGraphName(final Class<?> entityClass) {
		final NamedEntityGraph named = entityClass.getAnnotation(NamedEntityGraph.class);
		if(named.name().isEmpty()) {
			return entityClass.getAnnotation(Entity.class).name();
		}
		return named.name();
	}
}
