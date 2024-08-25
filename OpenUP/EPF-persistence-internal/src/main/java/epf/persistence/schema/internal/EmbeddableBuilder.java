package epf.persistence.schema.internal;

import java.util.stream.Collectors;
import jakarta.persistence.metamodel.EmbeddableType;
import epf.persistence.internal.Embeddable;

/**
 * @author PC
 *
 */
public class EmbeddableBuilder {

	/**
	 * @param entity
	 * @return
	 */
	public epf.persistence.schema.Embeddable build(final Embeddable<?> embeddable){
		final EmbeddableType<?> type = embeddable.getType();
		final epf.persistence.schema.Embeddable embeddableType = new epf.persistence.schema.Embeddable();
		final AttributeBuilder builder = new AttributeBuilder();
		final AttributeComparator comparator = new AttributeComparator();
		embeddableType.setAttributes(
				type
				.getAttributes()
				.stream()
				.map(builder::build)
				.sorted(comparator)
				.collect(Collectors.toList())
				);
		embeddableType.setType(type.getJavaType().getName());
		return embeddableType;
	}
}
