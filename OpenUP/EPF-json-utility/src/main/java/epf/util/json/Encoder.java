package epf.util.json;

import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

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
		final JsonObject jsonObject = JsonUtil.readObject(json);
		final JsonObject encodedJsonObject = Json
				.createObjectBuilder(jsonObject)
				.add(Naming.CLASS, object.getClass().getName())
				.build();
		return encodedJsonObject.toString();
	}
	
	/**
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String encode(final Object object) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return encode(object);
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
			final JsonObjectBuilder objectBuilder = Json
					.createObjectBuilder(JsonUtil.readObject(json))
					.add(Naming.CLASS, object.getClass().getName());
			arrayBuilder.add(objectBuilder);
		}
		return arrayBuilder.build().toString();
	}
	
	/**
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public String encodeArray(final List<Object> array) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return encodeArray(array);
		}
	}
}
