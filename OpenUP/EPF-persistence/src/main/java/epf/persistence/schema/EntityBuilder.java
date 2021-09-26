/**
 * 
 */
package epf.persistence.schema;

import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;
import epf.client.schema.Attribute;
import epf.client.schema.Table;
import epf.client.schema.util.AttributeComparator;
import epf.persistence.impl.Entity;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class EntityBuilder {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(EntityBuilder.class.getName());

	/**
	 * @param entity
	 * @return
	 */
	public epf.client.schema.Entity build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.client.schema.Entity entityType = new epf.client.schema.Entity();
		final AttributeBuilder builder = new AttributeBuilder();
		final AttributeComparator comparator = new AttributeComparator();
		entityType.setAttributes(
				type
				.getAttributes()
				.stream()
				.map(builder::build)
				.sorted(comparator)
				.collect(Collectors.toList())
				);
		entityType.setIdType(type.getIdType().getJavaType().getName());
		entityType.setType(type.getJavaType().getName());
		entityType.setName(type.getName());
		entityType.setEntityType(buildEntityType(type.getPersistenceType()));
		entityType.setSingleId(type.hasSingleIdAttribute());
		if(type.hasSingleIdAttribute()) {
			final Type<?> idType = type.getIdType();
			try {
				final Attribute id = builder.build(type.getId(idType.getJavaType()));
				entityType.setId(id);
			}
			catch(Exception ex) {
				LOGGER.throwing(getClass().getName(), "build", ex);
			}
		}
		entityType.setTable(buildTable(type.getJavaType()));
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
	
	/**
	 * @param cls
	 * @return
	 */
	protected static Table buildTable(final Class<?> cls) {
		final javax.persistence.Table tableAnnotation = cls.getAnnotation(javax.persistence.Table.class);
		final Table table = new Table();
		table.setCatalog(tableAnnotation.catalog());
		table.setName(tableAnnotation.name());
		table.setSchema(tableAnnotation.schema());
		return table;
	}
}
