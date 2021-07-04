/**
 * 
 */
package epf.portlet.persistence;

import java.util.Optional;
import javax.json.JsonObject;
import epf.client.schema.Attribute;

/**
 * @author PC
 *
 */
public class SingularAttribute extends BasicAttribute {
	
	/**
	 * 
	 */
	private final epf.client.schema.Entity entity;
	
	/**
	 * 
	 */
	private final String toString;

	/**
	 * @param object
	 * @param attribute
	 * @param entity
	 */
	protected SingularAttribute(final EntityObject object, final Attribute attribute, final epf.client.schema.Entity entity) {
		super(object, attribute);
		this.entity = entity;
		if(entity.isSingleId() && entity.getId() != null) {
			toString = entity.getId().getName();
		}
		else {
			final Optional<Attribute> toStringAttr = entity
					.getAttributes()
					.stream()
					.filter(attr -> String.class.getName().equals(attr.getType()))
					.findFirst();
			if(toStringAttr.isPresent()) {
				toString = toStringAttr.get().getName();
			}
			else {
				toString = null;
			}
		}
	}

	public epf.client.schema.Entity getEntity() {
		return entity;
	}
	
	/**
	 * @param object
	 * @return
	 */
	public String toString(final JsonObject object) {
		if(toString != null) {
			return AttributeUtil.getAsString(object.get(toString));
		}
		else {
			return object.toString();
		}
	}
}
