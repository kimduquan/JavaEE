package epf.portlet.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public class EntityObject {

	/**
	 * 
	 */
	private final JsonObject object;
	/**
	 * 
	 */
	private final Map<String, JsonValue> attributes;
	/**
	 * 
	 */
	private final Map<String, EntityObject> embeddedObjects;
	
	/**
	 * @param entity
	 * @param object
	 */
	public EntityObject(final JsonObject object) {
		this.object = object;
		attributes = new ConcurrentHashMap<>();
		embeddedObjects = new ConcurrentHashMap<>();
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void put(final String name, final JsonValue value) {
		attributes.put(name, value);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public JsonValue get(final String name) {
		return attributes.getOrDefault(name, object.get(name));
	}
	
	/**
	 * @param name
	 * @param embeddedObject
	 */
	public void putEmbedded(final String name, final EntityObject embeddedObject) {
		embeddedObjects.put(name, embeddedObject);
	}
	
	/**
	 * @return
	 */
	public JsonObject merge() {
		final JsonObjectBuilder builder = Json.createObjectBuilder(object);
		attributes.forEach(builder::add);
		embeddedObjects.forEach((name, embeddedObject) -> {
			builder.add(name, embeddedObject.merge());
		});
		return builder.build();
	}
}
