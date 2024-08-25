package epf.messaging.util.io;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.common.serialization.Deserializer;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class ObjectDeserializer implements Deserializer<Object> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(ObjectDeserializer.class.getName());

	@Override
	public Object deserialize(final String topic, final byte[] data) {
		try(ByteArrayInputStream input = new ByteArrayInputStream(data)){
			try(ObjectInputStream objectInput = new ObjectInputStream(input)){
				return objectInput.readObject();
			}
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[ObjectDeserializer.deserialize]", e);
			return new Object();
		}
	}
}
