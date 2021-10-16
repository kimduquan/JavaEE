package epf.messaging.util.io;

import java.io.ByteArrayInputStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import epf.util.json.Decoder;

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
		try(ByteArrayInputStream input = new ByteArrayInputStream(data)){
			return decoder.decode(jsonb, input);
		} 
		catch (Exception e) {
			return null;
		}
	}
}
