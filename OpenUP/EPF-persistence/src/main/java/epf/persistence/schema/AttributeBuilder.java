/**
 * 
 */
package epf.persistence.schema;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

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
}
