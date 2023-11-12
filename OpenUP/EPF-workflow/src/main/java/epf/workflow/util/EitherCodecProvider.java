package epf.workflow.util;

import java.lang.reflect.Type;
import java.util.List;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import epf.workflow.schema.TransitionOrEnd;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.StringOrObject;

/**
 * 
 */
public class EitherCodecProvider implements CodecProvider {

	@Override
	public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry) {
		return null;
	}

	@Override
	public <T> Codec<T> get(Class<T> clazz, List<Type> typeArguments, CodecRegistry registry) {
		if(clazz.equals(StringOrObject.class)) {
			@SuppressWarnings("unchecked")
			final Codec<T> codec = (Codec<T>) new StringOrObjectCodec<>(registry);
			return codec;
		}
		else if(clazz.equals(BooleanOrObject.class)) {
			@SuppressWarnings("unchecked")
			final Codec<T> codec = (Codec<T>) new BooleanOrObjectCodec<>(registry);
			return codec;
		}
		else if(clazz.equals(TransitionOrEnd.class)) {
			@SuppressWarnings("unchecked")
			final Codec<T> codec = (Codec<T>) new TransitionOrEndCodec(registry);
			return codec;
		}
		return null;
    }
}
