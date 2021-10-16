/**
 * 
 */
package epf.util.json;

import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class Encoder {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Encoder.class.getName());

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
						.add(Naming.CLASS, object.getClass().getName())
						.build();
				return encodedJsonObject.toString();
			}
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "encode", ex);
			throw ex;
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
							.add(Naming.CLASS, object.getClass().getName());
					arrayBuilder.add(objectBuilder);
				}
			}
			catch(Exception ex) {
				LOGGER.throwing(LOGGER.getName(), "encodeArray", ex);
				throw ex;
			}
		}
		return arrayBuilder.build().toString();
	}
}
