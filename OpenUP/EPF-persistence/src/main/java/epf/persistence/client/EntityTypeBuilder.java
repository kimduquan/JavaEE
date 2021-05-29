/**
 * 
 */
package epf.persistence.client;

import java.util.stream.Collectors;

import javax.persistence.metamodel.EntityType;

import epf.persistence.impl.Entity;

/**
 * @author PC
 *
 */
public class EntityTypeBuilder {

	/**
	 * @param entity
	 * @return
	 */
	public static epf.client.persistence.EntityType build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.client.persistence.EntityType entityType = new epf.client.persistence.EntityType();
		entityType.setAttributes(
				type
				.getDeclaredAttributes()
				.stream()
				.map(AttributeBuilder::build)
				.collect(Collectors.toSet())
				);
		entityType.setIdType(type.getIdType().getJavaType().getName());
		entityType.setType(type.getJavaType().getName());
		entityType.setName(type.getName());
		return entityType;
	}
}
