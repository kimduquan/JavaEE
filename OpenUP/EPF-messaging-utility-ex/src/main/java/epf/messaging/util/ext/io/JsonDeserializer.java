package epf.messaging.util.ext.io;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import epf.util.json.Decoder;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class JsonDeserializer implements Deserializer<Object> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(JsonDeserializer.class.getName());
	
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
			LOGGER.log(Level.SEVERE, "[JsonDeserializer.deserialize]", e);
			return new Object();
		}
	}
}
