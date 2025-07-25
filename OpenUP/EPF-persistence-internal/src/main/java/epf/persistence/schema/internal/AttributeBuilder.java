package epf.persistence.schema.internal;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.Attribute.PersistentAttributeType;
import jakarta.persistence.metamodel.Bindable;
import epf.persistence.schema.AttributeType;
import epf.persistence.schema.Column;

public class AttributeBuilder {

	public epf.persistence.schema.Attribute build(final Attribute<?, ?> attr){
		final epf.persistence.schema.Attribute attribute = new epf.persistence.schema.Attribute();
		attribute.setType(attr.getJavaType().getName());
		attribute.setName(attr.getName());
		attribute.setAttributeType(buildAttrbuteType(attr.getPersistentAttributeType()));
		attribute.setAssociation(attr.isAssociation());
		attribute.setCollection(attr.isCollection());
		if(attr instanceof Bindable) {
			final Bindable<?> bindable = (Bindable<?>) attr;
			attribute.setBindable(buildBindableType(bindable));
			attribute.setBindableType(bindable.getBindableJavaType().getName());
		}
		if(attr.getJavaType().isAnnotationPresent(jakarta.persistence.Column.class)) {
			final Column column = buildColumn(attr.getJavaType().getAnnotation(jakarta.persistence.Column.class));
			attribute.setColumn(column);
		}
		return attribute;
	}
	
	protected static AttributeType buildAttrbuteType(final PersistentAttributeType type) {
		AttributeType attrType = AttributeType.BASIC;
		switch(type) {
			case BASIC:
				attrType = AttributeType.BASIC;
				break;
			case ELEMENT_COLLECTION:
				attrType = AttributeType.ELEMENT_COLLECTION;
				break;
			case EMBEDDED:
				attrType = AttributeType.EMBEDDED;
				break;
			case MANY_TO_MANY:
				attrType = AttributeType.MANY_TO_MANY;
				break;
			case MANY_TO_ONE:
				attrType = AttributeType.MANY_TO_ONE;
				break;
			case ONE_TO_MANY:
				attrType = AttributeType.ONE_TO_MANY;
				break;
			case ONE_TO_ONE:
				attrType = AttributeType.ONE_TO_ONE;
				break;
			default:
				break;
		}
		return attrType;
	}
	
	protected static epf.persistence.schema.BindableType buildBindableType(final Bindable<?> bindable){
		epf.persistence.schema.BindableType type = epf.persistence.schema.BindableType.ENTITY_TYPE;
		switch(bindable.getBindableType()) {
		case ENTITY_TYPE:
			break;
		case PLURAL_ATTRIBUTE:
			type = epf.persistence.schema.BindableType.PLURAL_ATTRIBUTE;
			break;
		case SINGULAR_ATTRIBUTE:
			type = epf.persistence.schema.BindableType.SINGULAR_ATTRIBUTE;
			break;
		default:
			break;
		}
		return type;
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
