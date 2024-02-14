package epf.workflow.management.util;

import org.bson.codecs.configuration.CodecRegistry;
import epf.nosql.schema.StringOrArray;

/**
 * @param <T>
 */
public class StringOrArrayCodec<T> extends PrimitiveOrArrayCodec<StringOrArray<T>, String, T> {

	/**
	 * @param registry
	 */
	public StringOrArrayCodec(final CodecRegistry registry) {
		super(StringOrArray.class, registry, String.class);
	}

}
