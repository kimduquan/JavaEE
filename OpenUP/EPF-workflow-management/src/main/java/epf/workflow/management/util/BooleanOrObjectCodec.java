package epf.workflow.management.util;

import org.bson.codecs.configuration.CodecRegistry;
import epf.nosql.schema.BooleanOrObject;

/**
 * @param <T>
 */
public class BooleanOrObjectCodec<T> extends PrimitiveOrObjectCodec<BooleanOrObject<T>, Boolean, T> {

	/**
	 * @param registry
	 */
	public BooleanOrObjectCodec(final  CodecRegistry registry) {
		super(BooleanOrObject.class, registry, Boolean.class);
	}

}
