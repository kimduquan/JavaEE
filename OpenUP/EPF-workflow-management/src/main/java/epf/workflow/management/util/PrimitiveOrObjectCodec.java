package epf.workflow.management.util;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import epf.nosql.schema.Either;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;

/**
 * 
 */
public class PrimitiveOrObjectCodec<T extends Either<L, R>, L, R> implements Codec<T> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(PrimitiveOrObjectCodec.class.getName());
	
	/**
	 * 
	 */
	private transient final CodecRegistry registry;
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private transient final Codec<Map> mapCodec;
	
	/**
	 * 
	 */
	private transient final Class<L> leftClass;
	
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
	public PrimitiveOrObjectCodec(final Class<?> clazz, final CodecRegistry registry, final Class<L> leftClass) {
		this.registry = registry;
		mapCodec = registry.get(Map.class);
		this.leftClass = leftClass;
		this.leftCodec = registry.get(leftClass);
		this.clazz = (Class<T>) clazz;
	}
	
	protected void encodeObject(final BsonWriter writer, final EncoderContext encoderContext, final Object object) {
		final Class<?> clazz = object.getClass();
		if(JsonUtil.isPrimitive(clazz)) {
			@SuppressWarnings("unchecked")
			final Codec<Object> codec = (Codec<Object>) registry.get(clazz);
			codec.encode(writer, object, encoderContext);
		}
		else {
			try {
				final Map<String, Object> map = JsonUtil.toMap(object);
				map.put("class", clazz.getName());
				mapCodec.encode(writer, map, encoderContext);
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "EitherCodec.encodeObject", e);
			}
		}
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
			encodeObject(writer, encoderContext, value.getRight());
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
			Class<?> currentClass = null;
			boolean isDocument = false;
			switch(reader.getCurrentBsonType()) {
				case ARRAY:
					break;
				case BINARY:
					break;
				case BOOLEAN:
					currentClass = Boolean.class;
					break;
				case DATE_TIME:
					break;
				case DB_POINTER:
					break;
				case DECIMAL128:
					break;
				case DOCUMENT:
					currentClass = Map.class;
					isDocument = true;
					break;
				case DOUBLE:
					currentClass = Double.class;
					break;
				case END_OF_DOCUMENT:
					break;
				case INT32:
					currentClass = Integer.class;
					break;
				case INT64:
					currentClass = Long.class;
					break;
				case JAVASCRIPT:
					break;
				case JAVASCRIPT_WITH_SCOPE:
					break;
				case MAX_KEY:
					break;
				case MIN_KEY:
					break;
				case NULL:
					break;
				case OBJECT_ID:
					break;
				case REGULAR_EXPRESSION:
					break;
				case STRING:
					currentClass = String.class;
					break;
				case SYMBOL:
					break;
				case TIMESTAMP:
					break;
				case UNDEFINED:
					break;
				default:
					break;
			}
			if(currentClass != null) {
				if(leftClass.isAssignableFrom(currentClass)) {
					final L left = leftCodec.decode(reader, decoderContext);
					either.setLeft(left);
				}
				else if(isDocument) {
						final Map<?, ?> map = mapCodec.decode(reader, decoderContext);
						final Object className = map.get("class");
						if(className != null) {
							final Class<?> clazz = Either.class.getClassLoader().loadClass(className.toString());
							final Object value = JsonUtil.fromMap(map, clazz);
							if(leftClass.isAssignableFrom(clazz)) {
								@SuppressWarnings("unchecked")
								final L left = (L) value;
								either.setLeft(left);
							}
							else {
								@SuppressWarnings("unchecked")
								final R right = (R) value;
								either.setRight(right);
							}
						}
					}
			}
			return either;
		}
		catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "EitherCodec.decode", e);
			return null;
		}
	}
}
