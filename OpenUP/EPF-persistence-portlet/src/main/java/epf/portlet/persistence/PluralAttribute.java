/**
 * 
 */
package epf.portlet.persistence;

import epf.client.schema.Attribute;
import epf.client.schema.Entity;

/**
 * @author PC
 *
 */
public class PluralAttribute extends SingularAttribute {

	/**
	 * @param object
	 * @param attribute
	 * @param entity
	 */
	protected PluralAttribute(final EntityObject object, final Attribute attribute, final Entity entity) {
		super(object, attribute, entity);
	}
}
