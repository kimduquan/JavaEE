/**
 * 
 */
package epf.portlet.persistence;

import epf.client.schema.Attribute;
import epf.client.schema.AttributeType;
import epf.client.schema.Entity;

/**
 * @author PC
 *
 */
public class AttributeUtil {
	
	/**
	 * @param attribute
	 * @return
	 */
	public static boolean isBasic(final Attribute attribute) {
		return !attribute.isAssociation() 
				&& !attribute.isCollection() 
				&& attribute.getAttributeType().equals(AttributeType.BASIC);
	}
	
	/**
	 * @param entity
	 * @param attribute
	 * @return
	 */
	public static String getId(final Entity entity, final Attribute attribute) {
		return entity.getType() + "." + attribute.getName();
	}
}
