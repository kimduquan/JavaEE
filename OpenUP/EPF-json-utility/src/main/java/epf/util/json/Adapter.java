package epf.util.json;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * @author PC
 *
 */
public class Adapter implements JsonbAdapter<Object, JsonObject> {
	
	@Override
	public JsonObject adaptToJson(final Object obj) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			final String json = jsonb.toJson(obj);
			try(StringReader reader = new StringReader(json)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					final JsonObject jsonObject = jsonReader.readObject();
					return Json.
							createObjectBuilder(jsonObject)
							.add(Naming.CLASS, obj.getClass().getName())
							.build();
				}
			}
		}
	}

	@Override
	public Object adaptFromJson(final JsonObject obj) throws Exception {
		final String className = obj.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		final JsonObject adaptObject = Json.createObjectBuilder(obj).remove(Naming.CLASS).build();
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(adaptObject.toString(), cls);
		}
	}
}
