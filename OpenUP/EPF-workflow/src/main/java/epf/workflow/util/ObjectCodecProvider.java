package epf.workflow.util;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * 
 */
public class ObjectCodecProvider implements CodecProvider {

	@Override
	public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry) {
		if(clazz.equals(Object.class)) {
			@SuppressWarnings("unchecked")
			final Codec<T> codec = (Codec<T>) new ObjectCodec(ObjectCodecProvider.class.getClassLoader());
			return codec;
		}
		return null;
	}

}
