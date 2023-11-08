package epf.workflow.util;

import java.lang.reflect.Type;
import java.util.List;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import epf.workflow.schema.util.Either;

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
		if(clazz.equals(Either.class)) {
			final Class<?> leftClass = (Class<?>)typeArguments.get(0);
			final Class<?> rightClass = (Class<?>)typeArguments.get(1);
			@SuppressWarnings("unchecked")
			final Codec<T> codec = (Codec<T>) new EitherCodec<>(registry, leftClass, rightClass);
			return codec;
		}
		return null;
    }
}
