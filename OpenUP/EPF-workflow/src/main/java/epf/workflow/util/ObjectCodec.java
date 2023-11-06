package epf.workflow.util;

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
	@SuppressWarnings("rawtypes")
	private transient final Codec<Map> mapCodec;
	
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
	 * @param classLoader
	 * @param registry
	 */
	public ObjectCodec(final ClassLoader classLoader, final CodecRegistry registry) {
		mapCodec = registry.get(Map.class);
		booleanCodec = registry.get(boolean.class);
		stringCodec = registry.get(String.class);
		this.classLoader = classLoader;
	}

	@Override
	public void encode(final BsonWriter writer, final Object value, final EncoderContext encoderContext) {
		if(value == null) {
			writer.writeNull();
		}
		else if(value instanceof Boolean) {
			booleanCodec.encode(writer, (boolean)value, encoderContext);
		}
		else if(value instanceof String) {
			stringCodec.encode(writer, (String)value, encoderContext);
		}
		else {
			writer.writeStartDocument();
			writer.writeString("class", value.getClass().getName());
			try {
				final Map<String, Object> map = JsonUtil.toMap(value);
		        map.forEach((name, v) -> {
		        	if(v == null) {
		        		writer.writeNull(name);
		        	}
		        	else {
		        		encoderContext.encodeWithChildContext(this, writer, v);
		        	}
		        });
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
	        writer.writeEndDocument();
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
			case DOCUMENT:
				final Map<?, ?> map = mapCodec.decode(reader, decoderContext);
				try {
					final String className = map.get("class").toString();
					final Class<?> clazz = classLoader.loadClass(className);
					return JsonUtil.fromMap(map, clazz);
				} 
				catch (Exception e) {
					LOGGER.log(Level.SEVERE, e.getMessage());
				}
			case STRING:
				return stringCodec.decode(reader, decoderContext);
			case NULL:
				reader.readNull();
			default:
				break;
		}
		return null;
	}
}
