/**
 * 
 */
package epf.persistence.metamodel;

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
	public epf.client.persistence.Entity build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.client.persistence.Entity entityType = new epf.client.persistence.Entity();
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
	protected static epf.client.persistence.EntityType buildEntityType(final PersistenceType type) {
		epf.client.persistence.EntityType entityType = null;
		switch(type) {
		case BASIC:
			entityType = epf.client.persistence.EntityType.BASIC;
			break;
		case EMBEDDABLE:
			entityType = epf.client.persistence.EntityType.EMBEDDABLE;
			break;
		case ENTITY:
			entityType = epf.client.persistence.EntityType.ENTITY;
			break;
		case MAPPED_SUPERCLASS:
			entityType = epf.client.persistence.EntityType.MAPPED_SUPER_CLASS;
			break;
		default:
			break;
		}
		return entityType;
	}
}
