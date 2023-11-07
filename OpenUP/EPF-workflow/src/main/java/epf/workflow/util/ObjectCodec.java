package epf.workflow.util;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;

/**
 * 
 */
public class ObjectCodec implements Codec<Object> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(ObjectCodec.class.getName());
	
	/**
	 * 
	 */
	private transient final Codec<Boolean> booleanCodec;
	
	/**
	 * 
	 */
	private transient final Codec<String> stringCodec;
	
	/**
	 * 
	 */
	private transient final ClassLoader classLoader;
	
	/**
	 * 
	 */
	private transient final CodecRegistry registry;
	
	/**
	 * @param classLoader
	 * @param registry
	 */
	public ObjectCodec(final ClassLoader classLoader, final CodecRegistry registry) {
		booleanCodec = registry.get(Boolean.class);
		stringCodec = registry.get(String.class);
		this.classLoader = classLoader;
		this.registry = registry;
	}

	@Override
	public void encode(final BsonWriter writer, final Object value, final EncoderContext encoderContext) {
		if(value == null) {
			writer.writeNull();
		}
		else if(value instanceof Boolean) {
			booleanCodec.encode(writer, (Boolean)value, encoderContext);
		}
		else if(value instanceof String) {
			stringCodec.encode(writer, (String)value, encoderContext);
		}
		else {
			@SuppressWarnings("rawtypes")
			final Codec<OBJECT> codec = registry.get(OBJECT.class, Arrays.asList(value.getClass()));
			final OBJECT<Object> object = new OBJECT<>();
			object.setObject(value);
			codec.encode(writer, object, encoderContext);
		}
	}

	@Override
	public Class<Object> getEncoderClass() {
		return Object.class;
	}

	@Override
	public Object decode(final BsonReader reader, final DecoderContext decoderContext) {
		final BsonType bsonType = reader.readBsonType();
		switch(bsonType) {
			case BOOLEAN:
				return booleanCodec.decode(reader, decoderContext);
			case STRING:
				return stringCodec.decode(reader, decoderContext);
			case NULL:
				reader.readNull();
			default:
				final Codec<OBJECT> codec = registry.get(OBJECT.class, Arrays.asList(Map.class));
				final OBJECT<?> object = codec.decode(reader, decoderContext);
				final Map<?, ?> map = (Map<?, ?>) object.getObject();
				try {
					final Class<?> clazz = classLoader.loadClass(object.getType());
					return JsonUtil.fromMap(map, clazz);
				} 
				catch (Exception e) {
					LOGGER.log(Level.SEVERE, "ObjectCodec.decode", e);
				}
				break;
		}
		return null;
	}
}
