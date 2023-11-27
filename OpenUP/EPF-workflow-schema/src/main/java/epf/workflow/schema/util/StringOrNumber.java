package epf.workflow.schema.util;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * 
 */
@JsonbTypeSerializer(value = EitherJsonSerializer.class)
@JsonbTypeAdapter(value = StringOrNumberAdapter.class)
public class StringOrNumber extends Either<String, Number> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
