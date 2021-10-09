package epf.messaging.util.io;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import org.apache.kafka.common.serialization.Deserializer;

public class ObjectDeserializer implements Deserializer<Object> {

	@Override
	public Object deserialize(final String topic, final byte[] data) {
		try(ByteArrayInputStream input = new ByteArrayInputStream(data)){
			try(ObjectInputStream objectInput = new ObjectInputStream(input)){
				return objectInput.readObject();
			}
		} 
		catch (Exception e) {
			return null;
		}
	}
}
