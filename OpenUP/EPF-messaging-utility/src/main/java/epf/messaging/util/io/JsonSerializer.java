package epf.messaging.util.io;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.kafka.common.serialization.Serializer;
import epf.util.json.Encoder;

/**
 * @author PC
 *
 */
public class JsonSerializer implements Serializer<Object> {
	
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
			return encoder.encode(jsonb, object).getBytes();
		} 
		catch (Exception e) {
			return new byte[0];
		}
	}

}
