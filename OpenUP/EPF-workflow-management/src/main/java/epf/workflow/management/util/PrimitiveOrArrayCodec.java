package epf.workflow.management.util;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import epf.util.logging.LogManager;
import epf.workflow.schema.util.EitherArray;

/**
 * 
 */
public class PrimitiveOrArrayCodec<T extends EitherArray<L, R>, L, R> implements Codec<T> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(PrimitiveOrArrayCodec.class.getName());
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private transient final Codec<List> listCodec;
	
	/**
	 * 
	 */
	private transient final Codec<L> leftCodec; 
	
	/**
	 * 
	 */
	private transient final Class<T> clazz;
	
	/**
	 * @param clazz
	 * @param registry
	 * @param leftClass
	 */
	@SuppressWarnings("unchecked")
	public PrimitiveOrArrayCodec(final Class<?> clazz, final CodecRegistry registry, final Class<L> leftClass) {
		listCodec = registry.get(List.class);
		this.leftCodec = registry.get(leftClass);
		this.clazz = (Class<T>) clazz;
	}

	@Override
	public void encode(final BsonWriter writer, final T value, final EncoderContext encoderContext) {
		boolean isLeft = false;
		boolean isRight = false;
		if(value != null) {
			isLeft = value.isLeft();
			isRight = value.isRight();
		}
		if(isLeft) {
			leftCodec.encode(writer, value.getLeft(), encoderContext);
		}
		else if(isRight) {
			listCodec.encode(writer, value.getRight(), encoderContext);
		}
		else {
			writer.writeNull();
		}
	}

	@Override
	public Class<T> getEncoderClass() {
		return clazz;
	}

	@Override
	public T decode(final BsonReader reader, final DecoderContext decoderContext) {
		try {
			final T either = clazz.getConstructor().newInstance();
			boolean isArray = reader.getCurrentBsonType().equals(BsonType.ARRAY);
			if(isArray) {
				@SuppressWarnings("unchecked")
				final List<R> right = listCodec.decode(reader, decoderContext);
				either.setRight(right);
			}
			else {
				final L left = leftCodec.decode(reader, decoderContext);
				either.setLeft(left);
			}
			return either;
		}
		catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "PrimitiveOrArrayCodec.decode", e);
			return null;
		}
	}
}
