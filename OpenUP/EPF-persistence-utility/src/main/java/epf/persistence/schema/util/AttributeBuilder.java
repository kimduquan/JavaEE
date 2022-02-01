package epf.persistence.schema.util;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Bindable;
import epf.persistence.schema.client.AttributeType;

/**
 * @author PC
 *
 */
public class AttributeBuilder {

	/**
	 * @param attribute
	 * @return
	 */
	public epf.persistence.schema.client.Attribute build(final Attribute<?, ?> attr){
		final epf.persistence.schema.client.Attribute attribute = new epf.persistence.schema.client.Attribute();
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
		return attribute;
	}
	
	/**
	 * @param type
	 * @return
	 */
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
	
	/**
	 * @param bindable
	 * @return
	 */
	protected static epf.persistence.schema.client.BindableType buildBindableType(final Bindable<?> bindable){
		epf.persistence.schema.client.BindableType type = epf.persistence.schema.client.BindableType.ENTITY_TYPE;
		switch(bindable.getBindableType()) {
		case ENTITY_TYPE:
			break;
		case PLURAL_ATTRIBUTE:
			type = epf.persistence.schema.client.BindableType.PLURAL_ATTRIBUTE;
			break;
		case SINGULAR_ATTRIBUTE:
			type = epf.persistence.schema.client.BindableType.SINGULAR_ATTRIBUTE;
			break;
		default:
			break;
		}
		return type;
	}
}
