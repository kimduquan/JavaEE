package epf.messaging.util.ext.io;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.kafka.common.serialization.Serializer;
import epf.util.json.ext.Encoder;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class JsonSerializer implements Serializer<Object> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(JsonSerializer.class.getName());
	
	/**
	 * 
	 */
	private transient final Jsonb jsonb = JsonbBuilder.create();
	
	/**
	 * 
	 */
	private transient final Encoder encoder = new Encoder();

	@Override
	public byte[] serialize(final String topic, final Object object) {
		try {
			return encoder.encode(jsonb, object).getBytes("UTF-8");
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[JsonSerializer.serialize]", e);
			return new byte[0];
		}
	}

}
