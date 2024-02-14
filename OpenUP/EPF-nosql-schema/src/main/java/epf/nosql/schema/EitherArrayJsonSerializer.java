package epf.nosql.schema;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

/**
 * 
 */
public class EitherArrayJsonSerializer implements JsonbSerializer<EitherArray<? ,?>> {

	@Override
	public void serialize(final EitherArray<?, ?> obj, final JsonGenerator generator, final SerializationContext ctx) {
		if(obj.isLeft()) {
			ctx.serialize(obj.getLeft(), generator);
		}
		else if(obj.isRight()) {
			ctx.serialize(obj.getRight(), generator);
		}
		else {
			generator.writeNull();
		}
	}

}
