package epf.messaging.util.io;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import epf.util.json.Decoder;

/**
 * @author PC
 *
 */
public class JsonDeserializer implements Deserializer<Object> {
	
	/**
	 * 
	 */
	private transient final Jsonb jsonb = JsonbBuilder.create();
	
	/**
	 * 
	 */
	private transient final Decoder decoder = new Decoder();

	@Override
	public Object deserialize(final String topic, final byte[] data) {
		try {
			final String string = new String(data, "UTF-8");
			return decoder.decode(jsonb, string);
		} 
		catch (Exception e) {
			return null;
		}
	}
}
