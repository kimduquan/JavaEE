package epf.schema.internal;

import java.util.stream.Collectors;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.IdentifiableType;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.Type;
import epf.persistence.schema.Table;

public class EntityBuilder {

	public epf.persistence.schema.EntityType buildEntityType(final EntityType<?> type){
		final epf.persistence.schema.EntityType entityType = new epf.persistence.schema.EntityType();
		buildEntityType(type, entityType);
		return entityType;
	}
	
	protected static void buildEntityType(final EntityType<?> type, final epf.persistence.schema.EntityType entityType) {
		entityType.setBindableJavaType(type.getBindableJavaType().getName());
		entityType.setBindableType(EnumBuilder.buildBindableType(type.getBindableType()));
		entityType.setName(type.getName());
		entityType.setTable(buildTable(type.getJavaType()));
		buildIdentifiableType(type, entityType);
	}
	
	protected static void buildIdentifiableType(final IdentifiableType<?> type, epf.persistence.schema.IdentifiableType identifiableType) {
		final AttributeBuilder builder = new AttributeBuilder();
		final AttributeComparator comparator = new AttributeComparator();
		identifiableType.setHasSingleIdAttribute(type.hasSingleIdAttribute());
		identifiableType.setHasVersionAttribute(type.hasVersionAttribute());
		if(!type.hasSingleIdAttribute()) {
			identifiableType.setIdClassAttributes(
					type
					.getIdClassAttributes()
					.stream()
					.map(builder::buildSingularAttribute)
					.sorted(comparator)
					.collect(Collectors.toSet())
					);
		}
		if(type.getIdType() != null) {
			final epf.persistence.schema.Type idType = new epf.persistence.schema.Type();
			buildType(type.getIdType(), idType);
			identifiableType.setIdType(idType);
		}
		if(type.getSupertype() != null) {
			if(type.getSupertype() instanceof EntityType<?>) {
				epf.persistence.schema.EntityType supertype = new epf.persistence.schema.EntityType();
				buildEntityType((EntityType<?>)type.getSupertype(), supertype);
				identifiableType.setSupertype(supertype);
			}
			else {
				epf.persistence.schema.IdentifiableType supertype = new epf.persistence.schema.IdentifiableType();
				buildIdentifiableType(type.getSupertype(), supertype);
				identifiableType.setSupertype(supertype);
			}
		}
		buildManagedType(type, identifiableType);
	}
	
	protected static void buildManagedType(final ManagedType<?> type, final epf.persistence.schema.ManagedType managedType) {
		final AttributeBuilder builder = new AttributeBuilder();
		final AttributeComparator comparator = new AttributeComparator();
		managedType.setDeclaredSingularAttributes(
				type
				.getDeclaredSingularAttributes()
				.stream()
				.map(builder::buildSingularAttribute)
				.sorted(comparator)
				.collect(Collectors.toSet())
				);
		managedType.setSingularAttributes(
				type
				.getSingularAttributes()
				.stream()
				.map(builder::buildSingularAttribute)
				.sorted(comparator)
				.collect(Collectors.toSet())
				);
		managedType.setDeclaredPluralAttributes(
				type
				.getDeclaredPluralAttributes()
				.stream()
				.map(builder::buildPluralAttribute)
				.sorted(comparator)
				.collect(Collectors.toSet())
				);
		managedType.setPluralAttributes(
				type
				.getPluralAttributes()
				.stream()
				.map(builder::buildPluralAttribute)
				.sorted(comparator)
				.collect(Collectors.toSet())
				);
		buildType(type, managedType);
	}
	
	protected static void buildType(final Type<?> type, final epf.persistence.schema.Type newType) {
		newType.setJavaType(type.getJavaType().getName());
		newType.setPersistenceType(EnumBuilder.buildPersistenceType(type.getPersistenceType()));
	}
	
	protected static epf.persistence.schema.Type buildType(final Type<?> type) {
		final epf.persistence.schema.Type newType = new epf.persistence.schema.Type();
		buildType(type, newType);
		return newType;
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
