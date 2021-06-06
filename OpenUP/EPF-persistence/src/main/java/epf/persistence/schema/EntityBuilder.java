/**
 * 
 */
package epf.persistence.schema;

import java.util.stream.Collectors;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Type.PersistenceType;
import epf.persistence.impl.Entity;

/**
 * @author PC
 *
 */
public class EntityBuilder {

	/**
	 * @param entity
	 * @return
	 */
	public epf.client.schema.Entity build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.client.schema.Entity entityType = new epf.client.schema.Entity();
		final AttributeBuilder builder = new AttributeBuilder();
		entityType.setAttributes(
				type
				.getDeclaredAttributes()
				.stream()
				.map(builder::build)
				.collect(Collectors.toSet())
				);
		entityType.setIdType(type.getIdType().getJavaType().getName());
		entityType.setType(type.getJavaType().getName());
		entityType.setName(type.getName());
		entityType.setEntityType(buildEntityType(type.getPersistenceType()));
		return entityType;
	}
	
	/**
	 * @param type
	 * @return
	 */
	protected static epf.client.schema.EntityType buildEntityType(final PersistenceType type) {
		epf.client.schema.EntityType entityType = null;
		switch(type) {
		case BASIC:
			entityType = epf.client.schema.EntityType.BASIC;
			break;
		case EMBEDDABLE:
			entityType = epf.client.schema.EntityType.EMBEDDABLE;
			break;
		case ENTITY:
			entityType = epf.client.schema.EntityType.ENTITY;
			break;
		case MAPPED_SUPERCLASS:
			entityType = epf.client.schema.EntityType.MAPPED_SUPER_CLASS;
			break;
		default:
			break;
		}
		return entityType;
	}
}
