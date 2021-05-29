/**
 * 
 */
package epf.persistence.model;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

import epf.client.model.AttributeType;

/**
 * @author PC
 *
 */
public class AttributeBuilder {

	/**
	 * @param attribute
	 * @return
	 */
	public epf.client.model.Attribute build(final Attribute<?, ?> attr){
		final epf.client.model.Attribute attribute = new epf.client.model.Attribute();
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
		AttributeType attrType = AttributeType.Basic;
		switch(type) {
			case BASIC:
				attrType = AttributeType.Basic;
				break;
			case ELEMENT_COLLECTION:
				attrType = AttributeType.ElementCollection;
				break;
			case EMBEDDED:
				attrType = AttributeType.Embedded;
				break;
			case MANY_TO_MANY:
				attrType = AttributeType.ManyToMany;
				break;
			case MANY_TO_ONE:
				attrType = AttributeType.ManyToOne;
				break;
			case ONE_TO_MANY:
				attrType = AttributeType.OneToMany;
				break;
			case ONE_TO_ONE:
				attrType = AttributeType.OneToOne;
				break;
			default:
				break;
		}
		return attrType;
	}
}
