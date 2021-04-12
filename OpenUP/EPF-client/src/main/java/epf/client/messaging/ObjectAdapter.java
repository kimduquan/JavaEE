/**
 * 
 */
package epf.client.messaging;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * @author PC
 *
 */
public class ObjectAdapter implements JsonbAdapter<Object, JsonObject> {
	
	/**
	 * 
	 */
	public static final ObjectAdapter INSTANCE = new ObjectAdapter();
	
	/**
	 * 
	 */
	public static final JsonbConfig CONFIG = new JsonbConfig().withAdapters(INSTANCE);

	@Override
	public JsonObject adaptToJson(final Object obj) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			final String json = jsonb.toJson(obj);
			try(StringReader reader = new StringReader(json)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					final JsonObject jsonObject = jsonReader.readObject();
					if(!obj.getClass().isPrimitive() && !obj.getClass().isArray()) {
						return Json.
								createObjectBuilder(jsonObject)
								.add("class", obj.getClass().getName())
								.build();
					}
					return jsonObject;
				}
			}
		}
	}

	@Override
	public Object adaptFromJson(final JsonObject obj) throws Exception {
		if(obj.containsKey("class")) {
			final String className = obj.getString("class");
			final Class<?> cls = Class.forName(className);
			final JsonObject adaptObject = Json.createObjectBuilder(obj).remove("class").build();
			try(Jsonb jsonb = JsonbBuilder.create()){
				return jsonb.fromJson(adaptObject.toString(), cls);
			}
		}
		else {
			try(Jsonb jsonb = JsonbBuilder.create()){
				return jsonb.fromJson(obj.toString(), Object.class);
			}
		}
	}
}
