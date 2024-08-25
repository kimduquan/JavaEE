package epf.portlet.persistence;

import java.io.Serializable;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import epf.persistence.schema.Attribute;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
public class Identifiable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final JsonObject id;
	
	/**
	 * 
	 */
	private final String string;
	
	/**
	 * @param object
	 * @param entity
	 */
	public Identifiable(final JsonObject object, final epf.persistence.schema.Entity entity) {
		if(entity.isSingleId() && entity.getId() != null) {
			final JsonObjectBuilder builder = Json.createObjectBuilder();
			final JsonValue idValue = object.get(entity.getId().getName());
			id = builder.add(
					entity.getId().getName(), 
					idValue
					)
					.build();
			string = JsonUtil.toString(idValue);
		}
		else {
			id = object;
			final Optional<Attribute> stringAttr = entity
					.getAttributes()
					.stream()
					.filter(attr -> String.class.getName().equals(attr.getType()))
					.findFirst();
			if(stringAttr.isPresent()) {
				final JsonValue stringValue = object.get(stringAttr.get().getName());
				string = JsonUtil.toString(stringValue);
			}
			else {
				string = null;
			}
		}
	}
	
	/**
	 * @return
	 */
	public String getId() {
		return id.toString();
	}
	
	/**
	 *
	 */
	public String toString() {
		return string != null ? string : id.toString();
	}
}
