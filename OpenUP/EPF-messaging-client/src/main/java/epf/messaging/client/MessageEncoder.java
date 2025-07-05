package epf.messaging.client;

import java.util.logging.Logger;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import epf.util.json.ext.Adapter;
import epf.util.logging.LogManager;

public class MessageEncoder implements Encoder.Text<Object> {
	
	private static final Logger LOGGER = LogManager.getLogger(MessageEncoder.class.getName());
	
	private transient final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()));
	
	private transient final epf.util.json.ext.Encoder encoder = new epf.util.json.ext.Encoder();

	@Override
	public void init(final EndpointConfig config) {
		LOGGER.entering(getClass().getName(), "init");
	}

	@Override
	public void destroy() {
		LOGGER.exiting(getClass().getName(), "destroy");
	}

	@Override
	public String encode(final Object object) throws EncodeException {
		try {
			return encoder.encode(jsonb, object);
		}
		catch (Exception e) {
			LOGGER.throwing(encoder.getClass().getName(), "encode", e);
			throw new EncodeException(object, e.getMessage(), e);
		}
	}

}
