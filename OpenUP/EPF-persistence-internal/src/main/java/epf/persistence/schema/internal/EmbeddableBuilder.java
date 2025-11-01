package epf.persistence.schema.internal;

import jakarta.persistence.metamodel.EmbeddableType;

public class EmbeddableBuilder {

	public epf.persistence.schema.EmbeddableType build(final EmbeddableType<?> type){
		final epf.persistence.schema.EmbeddableType embeddableType = new epf.persistence.schema.EmbeddableType();
		EntityBuilder.buildManagedType(type, embeddableType);
		return embeddableType;
	}
}
