/**
 * 
 */
package epf.util.json;

import java.io.StringReader;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;

/**
 * @author PC
 *
 */
public class Encoder {

	/**
	 * @param jsonb
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String encode(final Jsonb jsonb, final Object object) throws Exception {
		final String json = jsonb.toJson(object);
		try(StringReader reader = new StringReader(json)){
			try(JsonReader jsonReader = Json.createReader(reader)){
				final JsonObject jsonObject = jsonReader.readObject();
				final JsonObject encodedJsonObject = Json
						.createObjectBuilder(jsonObject)
						.add("class", object.getClass().getName())
						.build();
				return encodedJsonObject.toString();
			}
		}
	}
	
	/**
	 * @param jsonb
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public String encodeArray(final Jsonb jsonb, final List<Object> array) throws Exception {
		final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(Object object : array) {
			final String json = jsonb.toJson(object);
			try(StringReader reader = new StringReader(json)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					final JsonObjectBuilder objectBuilder = Json
							.createObjectBuilder(jsonReader.readObject())
							.add("class", object.getClass().getName());
					arrayBuilder.add(objectBuilder);
				}
			}
		}
		return arrayBuilder.build().toString();
	}
}
