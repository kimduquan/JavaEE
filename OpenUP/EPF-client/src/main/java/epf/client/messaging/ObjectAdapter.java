/**
 * 
 */
package epf.client.messaging;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.adapter.JsonbAdapter;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class ObjectAdapter implements JsonbAdapter<Object, JsonObject> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(ObjectAdapter.class.getName());
	
	/**
	 * 
	 */
	public static final ObjectAdapter INSTANCE = new ObjectAdapter();
	
	/**
	 * 
	 */
	public static final JsonbConfig CONFIG = new JsonbConfig()
			.withAdapters(INSTANCE);

	@Override
	public JsonObject adaptToJson(final Object obj) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create(CONFIG)){
			final String json = jsonb.toJson(obj);
			try(StringReader reader = new StringReader(json)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					final JsonObject jsonObject = jsonReader.readObject();
					return Json.
							createObjectBuilder(jsonObject)
							.add("class", obj.getClass().getName())
							.build();
				}
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "adaptToJson", ex);
			throw ex;
		}
	}

	@Override
	public Object adaptFromJson(final JsonObject obj) throws Exception {
		try {
			final String className = obj.getString("class");
			final Class<?> cls = Class.forName(className);
			final JsonObject adaptObject = Json.createObjectBuilder(obj).remove("class").build();
			try(Jsonb jsonb = JsonbBuilder.create(CONFIG)){
				return jsonb.fromJson(adaptObject.toString(), cls);
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "adaptToJson", ex);
			throw ex;
		}
	}
}
