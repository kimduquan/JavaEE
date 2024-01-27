package epf.workflow.management.util;

import org.bson.codecs.configuration.CodecRegistry;
import epf.workflow.schema.util.StringOrObject;

/**
 * @param <T>
 */
public class StringOrObjectCodec<T> extends PrimitiveOrObjectCodec<StringOrObject<T>, String, T> {

	/**
	 * @param registry
	 */
	public StringOrObjectCodec(final CodecRegistry registry) {
		super(StringOrObject.class, registry, String.class);
	}

}
