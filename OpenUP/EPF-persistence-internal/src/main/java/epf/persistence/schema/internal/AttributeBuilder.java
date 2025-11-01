package epf.persistence.schema.internal;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import epf.persistence.schema.Column;

public class AttributeBuilder {
	
	public epf.persistence.schema.PluralAttribute buildPluralAttribute(final PluralAttribute<?, ?, ?> attr) {
		final epf.persistence.schema.PluralAttribute attribute = new epf.persistence.schema.PluralAttribute();
		attribute.setCollectionType(EnumBuilder.buildCollectionType(attr.getCollectionType()));
		attribute.setElementType(EntityBuilder.buildType(attr.getElementType()));
		attribute.setBindableJavaType(attr.getBindableJavaType().getName());
		attribute.setBindableType(EnumBuilder.buildBindableType(attr.getBindableType()));
		buildAttribute(attr, attribute);
		return attribute;
	}
	
	public epf.persistence.schema.SingularAttribute buildSingularAttribute(final SingularAttribute<?, ?> attr) {
		final epf.persistence.schema.SingularAttribute attribute = new epf.persistence.schema.SingularAttribute();
		attribute.setId(attr.isId());
		attribute.setOptional(attr.isOptional());
		attribute.setType(EntityBuilder.buildType(attr.getType()));
		attribute.setVersion(attr.isVersion());
		attribute.setBindableJavaType(attr.getBindableJavaType().getName());
		attribute.setBindableType(EnumBuilder.buildBindableType(attr.getBindableType()));
		buildAttribute(attr, attribute);
		return attribute;
	}

	protected static void buildAttribute(final Attribute<?, ?> attr, final epf.persistence.schema.Attribute attribute){
		attribute.setAssociation(attr.isAssociation());
		attribute.setCollection(attr.isCollection());
		if(attr.getJavaType().isAnnotationPresent(jakarta.persistence.Column.class)) {
			final Column column = buildColumn(attr.getJavaType().getAnnotation(jakarta.persistence.Column.class));
			attribute.setColumn(column);
		}
		attribute.setDeclaringType(attr.getDeclaringType().getJavaType().getName());
		attribute.setJavaMember(attr.getJavaMember().getName());
		attribute.setJavaType(attr.getJavaType().getName());
		attribute.setName(attr.getName());
		attribute.setPersistentAttributeType(EnumBuilder.buildPersistentAttributeType(attr.getPersistentAttributeType()));
	}
	
	protected static Column buildColumn(final jakarta.persistence.Column columnAnnotation) {
		final Column column = new Column();
		column.setColumnDefinition(columnAnnotation.columnDefinition());
		column.setName(columnAnnotation.name());
		column.setTable(columnAnnotation.table());
		column.setInsertable(columnAnnotation.insertable());
		column.setNullable(columnAnnotation.nullable());
		column.setUnique(columnAnnotation.unique());
		column.setUpdatable(columnAnnotation.updatable());
		column.setLength(columnAnnotation.length());
		column.setPrecision(columnAnnotation.precision());
		column.setScale(columnAnnotation.scale());
		return column;
	}
}
