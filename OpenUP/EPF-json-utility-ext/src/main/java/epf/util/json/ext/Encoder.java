package epf.util.json.ext;

import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class Encoder {

	public String encode(final Jsonb jsonb, final Object object) throws Exception {
		final String json = jsonb.toJson(object);
		final JsonObject jsonObject = JsonUtil.readObject(json);
		final JsonObject encodedJsonObject = Json
				.createObjectBuilder(jsonObject)
				.add(Naming.CLASS, object.getClass().getName())
				.build();
		return encodedJsonObject.toString();
	}
	
	public String encode(final Object object) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return encode(jsonb, object);
		}
	}
	
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
	
	public String encodeArray(final List<Object> array) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return encodeArray(jsonb, array);
		}
	}
}
