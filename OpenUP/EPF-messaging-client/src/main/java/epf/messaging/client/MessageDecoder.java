/**
 * 
 */
package epf.messaging.client;

import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import epf.util.json.Adapter;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class MessageDecoder implements Decoder.Text<Object> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(MessageDecoder.class.getName());
	
	/**
	 * 
	 */
	private transient final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()));
	
	/**
	 * 
	 */
	private transient final epf.util.json.Decoder decoder = new epf.util.json.Decoder();
	
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
		try{
			return decoder.decode(jsonb, string);
		}
		catch (Exception e) {
			LOGGER.throwing(decoder.getClass().getName(), "decode", e);
			throw new DecodeException(string, e.getMessage(), e);
		}
	}

	@Override
	public boolean willDecode(final String string) {
		return !string.isEmpty();
	}

}
