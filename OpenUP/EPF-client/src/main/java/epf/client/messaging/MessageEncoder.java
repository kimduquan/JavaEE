/**
 * 
 */
package epf.client.messaging;

import java.util.logging.Logger;
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
		try{
			return ObjectAdapter.INSTANCE.adaptToJson(object).toString();
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "encode", e);
			throw new EncodeException(object, e.getMessage(), e);
		}
	}

}
