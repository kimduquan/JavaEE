package epf.persistence.schema;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Bindable;

import epf.client.schema.AttributeType;

/**
 * @author PC
 *
 */
public class AttributeBuilder {

	/**
	 * @param attribute
	 * @return
	 */
	public epf.client.schema.Attribute build(final Attribute<?, ?> attr){
		final epf.client.schema.Attribute attribute = new epf.client.schema.Attribute();
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
	protected static epf.client.schema.BindableType buildBindableType(final Bindable<?> bindable){
		epf.client.schema.BindableType type = epf.client.schema.BindableType.ENTITY_TYPE;
		switch(bindable.getBindableType()) {
		case ENTITY_TYPE:
			break;
		case PLURAL_ATTRIBUTE:
			type = epf.client.schema.BindableType.PLURAL_ATTRIBUTE;
			break;
		case SINGULAR_ATTRIBUTE:
			type = epf.client.schema.BindableType.SINGULAR_ATTRIBUTE;
			break;
		default:
			break;
		}
		return type;
	}
}
