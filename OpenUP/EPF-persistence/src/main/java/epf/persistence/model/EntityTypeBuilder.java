/**
 * 
 */
package epf.persistence.model;

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
	public static epf.client.model.Entity build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.client.model.Entity entityType = new epf.client.model.Entity();
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
