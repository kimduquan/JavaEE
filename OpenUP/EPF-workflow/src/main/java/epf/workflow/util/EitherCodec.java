package epf.workflow.util;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import epf.workflow.schema.util.Either;

/**
 * 
 */
public class EitherCodec<L, R> implements Codec<Either<L, R>> {
	
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
	private transient final Class<L> leftClass;
	
	/**
	 * 
	 */
	private transient final Class<R> rightClass;
	
	/**
	 * @param registry
	 * @param leftClass
	 * @param rightClass
	 */
	public EitherCodec(final CodecRegistry registry, Class<L> leftClass, Class<R> rightClass) {
		this.leftCodec = registry.get(leftClass);
		this.rightCodec = registry.get(rightClass);
		this.leftClass = leftClass;
		this.rightClass = rightClass;
	}

	@Override
	public void encode(final BsonWriter writer, final Either<L, R> value, final EncoderContext encoderContext) {
		if(value == null) {
			writer.writeNull();
		}
		else if(value.isLeft()) {
			leftCodec.encode(writer, value.getLeft(), encoderContext);
		}
		else if(value.isRight()) {
			rightCodec.encode(writer, value.getRight(), encoderContext);
		}
	}

	@Override
	public Class<Either<L, R>> getEncoderClass() {
		final Either<L, R> either = new Either<L, R>();
		@SuppressWarnings("unchecked")
		final Class<Either<L, R>> clazz = (Class<Either<L, R>>) either.getClass();
		return clazz;
	}

	@Override
	public Either<L, R> decode(final BsonReader reader, final DecoderContext decoderContext) {
		final Either<L, R> either = new Either<>();
		boolean isLeft = false;
		boolean isRight = false;
		final BsonType bsonType = reader.readBsonType();
		switch(bsonType) {
			case DOCUMENT:
				if(!leftClass.isPrimitive()) {
					isLeft = true;
				}
				else if(!rightClass.isPrimitive()) {
					isRight = true;
				}
				break;
			case NULL:
				reader.readNull();
				break;
			default:
				if(leftClass.isPrimitive()) {
					isLeft = true;
				}
				else if(rightClass.isPrimitive()) {
					isRight = true;
				}
				break;
		}

		if(isLeft) {
			final L left = leftCodec.decode(reader, decoderContext);
			either.setLeft(left);
		}
		else if(isRight) {
			final R right = rightCodec.decode(reader, decoderContext);
			either.setRight(right);
		}
		return either;
	}
}
