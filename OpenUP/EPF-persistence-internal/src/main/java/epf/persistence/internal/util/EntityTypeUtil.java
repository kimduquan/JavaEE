package epf.persistence.internal.util;

import java.util.Optional;
import javax.persistence.Table;
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
	
	/**
	 * @param metamodel
	 * @param cls
	 * @return
	 */
	static Optional<EntityType<?>> findEntityType(final Metamodel metamodel, final Class<?> cls){
		return metamodel.getEntities()
				.stream()
				.filter(type -> type.getJavaType().getName().equals(cls.getName()))
				.findFirst();
	}
	
	/**
	 * @param entityType
	 * @return
	 */
	static Optional<String> getSchema(final EntityType<?> entityType) {
		Optional<String> schema = Optional.empty();
		final Table table = entityType.getJavaType().getAnnotation(Table.class);
		if(table != null) {
			schema = Optional.ofNullable(table.schema());
		}
		return schema;
	}
}
