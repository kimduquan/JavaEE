package epf.util.concurrent.io;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import org.apache.kafka.common.serialization.Serializer;

public class ObjectSerializer implements Serializer<java.io.Serializable> {

	@Override
	public byte[] serialize(final String topic, final java.io.Serializable data) {
		try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
			try(ObjectOutputStream objectOutput = new ObjectOutputStream(output)){
				objectOutput.writeObject(data);
			}
			return output.toByteArray();
		} 
		catch (Exception e) {
			return new byte[0];
		}
	}

}
