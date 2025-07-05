package epf.persistence.util;

import java.util.Optional;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

public interface EntityTypeUtil {
	
	static Optional<EntityType<?>> findEntityType(final Metamodel metamodel, final String name){
		return metamodel.getEntities()
				.stream()
				.filter(type -> type.getName().equals(name))
				.findFirst();
	}
	
	@SuppressWarnings("unchecked")
	static <T> Optional<EntityType<T>> findEntityType(final Metamodel metamodel, final Class<T> cls){
		return metamodel.getEntities()
				.stream()
				.filter(type -> type.getJavaType().getName().equals(cls.getName()))
				.map(type -> (EntityType<T>)type)
				.findFirst();
	}
	
	static Optional<String> getSchema(final EntityType<?> entityType) {
		Optional<String> schema = Optional.empty();
		final Table table = entityType.getJavaType().getAnnotation(Table.class);
		if(table != null) {
			schema = Optional.ofNullable(table.schema());
		}
		return schema;
	}
}
