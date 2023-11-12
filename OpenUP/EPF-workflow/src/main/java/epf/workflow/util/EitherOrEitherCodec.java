package epf.workflow.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import epf.util.logging.LogManager;
import epf.workflow.schema.util.Either;
import epf.workflow.schema.util.EitherOrEither;

/**
 * @param <T>
 */
public class EitherOrEitherCodec<L extends Either<?, ?>, R extends Either<?, ?>> implements Codec<EitherOrEither<L, R>> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(EitherOrEitherCodec.class.getName());
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private transient final Codec<Map> mapCodec;
	
	/**
	 * 
	 */
	private transient final Codec<L> leftCodec;
	
	/**
	 * 
	 */
	private transient final Codec<R> rightCodec;
	
	/**
	 * 
	 */
	private final String leftKey;
	
	/**
	 * 
	 */
	private final String rightKey;
	
	/**
	 * 
	 */
	private transient final Class<EitherOrEither<L, R>> clazz;
	
	/**
	 * @param registry
	 * @param leftKey
	 * @param leftCodec
	 * @param rightKey
	 * @param rightCodec
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public EitherOrEitherCodec(final CodecRegistry registry, final String leftKey, final Codec<L> leftCodec, final String rightKey, final Codec<R> rightCodec, final Class<? extends EitherOrEither<?, ?>> clazz) {
		this.leftCodec = leftCodec;
		this.rightCodec = rightCodec;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.clazz = (Class<EitherOrEither<L, R>>) clazz;
		this.mapCodec = registry.get(Map.class);
	}

	@Override
	public void encode(BsonWriter writer, EitherOrEither<L, R> value, EncoderContext encoderContext) {
		final Map<Object, Object> map = new HashMap<>();
		if(value.isLeft()) {
			Object left = null;
			if(value.getLeft().isLeft()) {
				left = value.getLeft().getLeft();
			}
			else if(value.getLeft().isRight()) {
				left = value.getLeft().getRight();
			}
			map.put(leftKey, left);
		}
		else if(value.isRight()) {
			Object right = null;
			if(value.getRight().isLeft()) {
				right = value.getRight().getLeft();
			}
			else if(value.getRight().isRight()) {
				right = value.getRight().getRight();
			}
			map.put(rightKey, right);
		}
		mapCodec.encode(writer, map, encoderContext);
	}

	@Override
	public Class<EitherOrEither<L, R>> getEncoderClass() {
		return clazz;
	}

	@Override
	public EitherOrEither<L, R> decode(BsonReader reader, DecoderContext decoderContext) {
		try {
			final EitherOrEither<L, R> eitherOrEither = clazz.getConstructor().newInstance();
			final String name = reader.readName();
			if(leftKey.equals(name)) {
				final L left = leftCodec.decode(reader, decoderContext);
				eitherOrEither.setLeft(left);
			}
			else if(rightKey.equals(name)) {
				final R right = rightCodec.decode(reader, decoderContext);
				eitherOrEither.setRight(right);
			}
			return eitherOrEither;
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "EitherOrEitherCodec.decode", ex);
			return null;
		}
	}
}
