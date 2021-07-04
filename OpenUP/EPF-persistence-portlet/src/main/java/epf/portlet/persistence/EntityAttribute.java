/**
 * 
 */
package epf.portlet.persistence;

import epf.client.schema.Attribute;
import epf.client.schema.AttributeType;

/**
 * @author PC
 *
 */
public class EntityAttribute {

	/**
	 * 
	 */
	private final Attribute attribute;
	/**
	 * 
	 */
	private final EntityObject object;
	
	/**
	 * @param object
	 * @param attribute
	 */
	protected EntityAttribute(final EntityObject object, final Attribute attribute) {
		this.attribute = attribute;
		this.object = object;
	}
	
	/**
	 * @return
	 */
	public String getValue() {
		return AttributeUtil.getAsString(object.get(attribute.getName()));
	}
	
	/**
	 * @param value
	 */
	public void setValue(final String value) {
		AttributeUtil.setValue(object, attribute, value);
	}

	public Attribute getAttribute() {
		return attribute;
	}
	
	public boolean isBasic() {
		return AttributeUtil.isBasic(attribute);
	}
	
	public boolean isEmbedded() {
		return AttributeType.EMBEDDED.equals(attribute.getAttributeType());
	}
}
