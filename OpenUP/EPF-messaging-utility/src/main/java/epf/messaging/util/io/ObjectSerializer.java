package epf.messaging.util.io;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.common.serialization.Serializer;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class ObjectSerializer implements Serializer<java.io.Serializable> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(ObjectSerializer.class.getName());

	@Override
	public byte[] serialize(final String topic, final java.io.Serializable data) {
		try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
			try(ObjectOutputStream objectOutput = new ObjectOutputStream(output)){
				objectOutput.writeObject(data);
			}
			return output.toByteArray();
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[ObjectSerializer.serialize]", e);
			return new byte[0];
		}
	}

}
