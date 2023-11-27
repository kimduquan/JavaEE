package epf.workflow.util;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import epf.workflow.schema.util.StringOrNumber;

/**
 * 
 */
public class StringOrNumberCodec implements Codec<StringOrNumber> {
	
	/**
	 * 
	 */
	private transient final Codec<String> stringCodec;
	
	/**
	 * 
	 */
	private transient final Codec<Number> numberCodec;
	
	public StringOrNumberCodec(final CodecRegistry registry) {
		stringCodec = registry.get(String.class);
		numberCodec = registry.get(Number.class);
	}

	@Override
	public void encode(BsonWriter writer, StringOrNumber value, EncoderContext encoderContext) {
		if(value.isLeft()) {
			stringCodec.encode(writer, value.getLeft(), encoderContext);
		}
		else if(value.isRight()) {
			numberCodec.encode(writer, value.getRight(), encoderContext);
		}
		else {
			writer.writeNull();
		}
	}

	@Override
	public Class<StringOrNumber> getEncoderClass() {
		return StringOrNumber.class;
	}

	@Override
	public StringOrNumber decode(BsonReader reader, DecoderContext decoderContext) {
		final StringOrNumber stringOrNumber = new StringOrNumber();
		if(BsonType.STRING.equals(reader.getCurrentBsonType())) {
			final String string = stringCodec.decode(reader, decoderContext);
			stringOrNumber.setLeft(string);
		}
		else if(BsonType.DOUBLE.equals(reader.getCurrentBsonType()) || BsonType.INT32.equals(reader.getCurrentBsonType())) {
			final Number number = numberCodec.decode(reader, decoderContext);
			stringOrNumber.setRight(number);
		}
		return stringOrNumber;
	}
}
