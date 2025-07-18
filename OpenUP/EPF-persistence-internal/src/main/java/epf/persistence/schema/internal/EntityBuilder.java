package epf.persistence.schema.internal;

import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Type;
import jakarta.persistence.metamodel.Type.PersistenceType;
import epf.persistence.internal.Entity;
import epf.persistence.schema.Attribute;
import epf.persistence.schema.Table;
import epf.util.logging.LogManager;

public class EntityBuilder {
	
	private static final Logger LOGGER = LogManager.getLogger(EntityBuilder.class.getName());

	public epf.persistence.schema.Entity build(final Entity<?> entity){
		final EntityType<?> type = entity.getType();
		final epf.persistence.schema.Entity entityType = new epf.persistence.schema.Entity();
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
	
	protected static epf.persistence.schema.EntityType buildEntityType(final PersistenceType type) {
		epf.persistence.schema.EntityType entityType = null;
		switch(type) {
		case BASIC:
			entityType = epf.persistence.schema.EntityType.BASIC;
			break;
		case EMBEDDABLE:
			entityType = epf.persistence.schema.EntityType.EMBEDDABLE;
			break;
		case ENTITY:
			entityType = epf.persistence.schema.EntityType.ENTITY;
			break;
		case MAPPED_SUPERCLASS:
			entityType = epf.persistence.schema.EntityType.MAPPED_SUPER_CLASS;
			break;
		default:
			break;
		}
		return entityType;
	}
	
	protected static Table buildTable(final Class<?> cls) {
		final jakarta.persistence.Table tableAnnotation = cls.getAnnotation(jakarta.persistence.Table.class);
		final Table table = new Table();
		table.setCatalog(tableAnnotation.catalog());
		table.setName(tableAnnotation.name());
		table.setSchema(tableAnnotation.schema());
		return table;
	}
}
