/**
 * 
 */
package epf.persistence.model;

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
	public epf.client.model.Entity build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.client.model.Entity entityType = new epf.client.model.Entity();
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
	protected static epf.client.model.EntityType buildEntityType(final PersistenceType type) {
		epf.client.model.EntityType entityType = null;
		switch(type) {
		case BASIC:
			entityType = epf.client.model.EntityType.Basic;
			break;
		case EMBEDDABLE:
			entityType = epf.client.model.EntityType.Embeddable;
			break;
		case ENTITY:
			entityType = epf.client.model.EntityType.Entity;
			break;
		case MAPPED_SUPERCLASS:
			entityType = epf.client.model.EntityType.MappedSuperClass;
			break;
		default:
			break;
		}
		return entityType;
	}
}
