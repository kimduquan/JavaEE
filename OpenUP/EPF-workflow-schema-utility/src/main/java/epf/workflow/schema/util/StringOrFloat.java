package epf.workflow.schema.util;

import epf.nosql.schema.Either;
import epf.nosql.schema.EitherJsonSerializer;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * 
 */
@JsonbTypeSerializer(value = EitherJsonSerializer.class)
@JsonbTypeAdapter(value = StringOrFloatAdapter.class)
public class StringOrFloat extends Either<String, Float> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
