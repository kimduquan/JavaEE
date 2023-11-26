package epf.workflow.schema.util;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

/**
 * 
 */
public class EitherArrayJsonSerializer implements JsonbSerializer<Either<? ,?>> {

	@Override
	public void serialize(final Either<?, ?> obj, final JsonGenerator generator, final SerializationContext ctx) {
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
