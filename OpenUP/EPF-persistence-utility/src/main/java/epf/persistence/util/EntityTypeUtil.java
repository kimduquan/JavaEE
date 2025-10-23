package epf.persistence.util;

import java.util.Optional;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

public interface EntityTypeUtil {
	
	static Optional<EntityType<?>> findEntityType(final Metamodel metamodel, final String schema, final String name) {
		return metamodel.getEntities()
				.stream()
				.filter(entityType -> {
					if(entityType.getName().equals(name)) {
						final Optional<String> entitySchema = getSchema(entityType);
						if(entitySchema.isPresent()) {
							return entitySchema.get().equals(schema);
						}
					}
					return false;
				})
				.findFirst();
	}
	
	static Optional<String> getSchema(final EntityType<?> entityType) {
		Optional<String> entitySchema = Optional.empty();
		final Table table = entityType.getJavaType().getAnnotation(Table.class);
		if(table != null) {
			entitySchema = Optional.ofNullable(table.schema());
		}
		return entitySchema;
	}
}
