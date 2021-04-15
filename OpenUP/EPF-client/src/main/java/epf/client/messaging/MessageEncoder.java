/**
 * 
 */
package epf.client.messaging;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageEncoder implements Encoder.Text<Object> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageEncoder.class.getName());
	
	/**
	 * 
	 */
	private transient final JsonbConfig config = new JsonbConfig().withAdapters(new MessageAdapter());

	@Override
	public void init(final EndpointConfig config) {
		LOGGER.entering(getClass().getName(), "init");
	}

	@Override
	public void destroy() {
		LOGGER.entering(getClass().getName(), "destroy");
	}

	@Override
	public String encode(final Object object) throws EncodeException {
		try(Jsonb jsonb = JsonbBuilder.create(config)){
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
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "encode", e);
			throw new EncodeException(object, e.getMessage(), e);
		}
	}

}
