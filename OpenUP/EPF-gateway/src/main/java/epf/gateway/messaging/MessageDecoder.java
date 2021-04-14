/**
 * 
 */
package epf.gateway.messaging;

import java.util.logging.Logger;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageDecoder implements Decoder.Text<Message> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageDecoder.class.getName());

	@Override
	public void init(final EndpointConfig config) {
		LOGGER.entering(getClass().getName(), "init", config);
	}

	@Override
	public void destroy() {
		LOGGER.exiting(getClass().getName(), "destroy");
	}

	@Override
	public Message decode(final String text) throws DecodeException {
		return new Message(text);
	}

	@Override
	public boolean willDecode(final String text) {
		return !text.isEmpty();
	}

}
