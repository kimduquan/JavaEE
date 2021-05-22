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
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageDecoder implements Decoder.Text<Object> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageDecoder.class.getName());
	
	/**
	 * 
	 */
	private transient final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new MessageAdapter()));
	
	@Override
	public void init(final EndpointConfig config) {
		LOGGER.entering(getClass().getName(), "init");
	}

	@Override
	public void destroy() {
		LOGGER.exiting(getClass().getName(), "destroy");
	}

	@Override
	public Object decode(final String string) throws DecodeException {
		try(StringReader reader = new StringReader(string)){
			try(JsonReader jsonReader = Json.createReader(reader)){
				final JsonObject jsonObject = jsonReader.readObject();
				final String className = jsonObject.getString("class");
				final Class<?> cls = Class.forName(className);
				return jsonb.fromJson(string, cls);
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "decode", e);
			throw new DecodeException(string, e.getMessage(), e);
		}
	}

	@Override
	public boolean willDecode(final String string) {
		return !string.isEmpty();
	}

}
