/**
 * 
 */
package epf.persistence.schema;

import java.util.stream.Collectors;
import javax.persistence.metamodel.EmbeddableType;
import epf.client.schema.util.AttributeComparator;
import epf.persistence.impl.Embeddable;

/**
 * @author PC
 *
 */
public class EmbeddableBuilder {

	/**
	 * @param entity
	 * @return
	 */
	public epf.client.schema.Embeddable build(final Embeddable<?> embeddable){
		final EmbeddableType<?> type = embeddable.getType();
		final epf.client.schema.Embeddable embeddableType = new epf.client.schema.Embeddable();
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
