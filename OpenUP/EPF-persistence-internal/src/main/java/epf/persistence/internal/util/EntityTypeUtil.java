package epf.persistence.internal.util;

import java.util.Optional;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * @author PC
 *
 */
public interface EntityTypeUtil {
	
	/**
	 * @param metamodel
	 * @param name
	 * @return
	 */
	static Optional<EntityType<?>> findEntityType(final Metamodel metamodel, final String name){
		return metamodel.getEntities()
				.stream()
				.filter(type -> type.getName().equals(name))
				.findFirst();
	}
}
