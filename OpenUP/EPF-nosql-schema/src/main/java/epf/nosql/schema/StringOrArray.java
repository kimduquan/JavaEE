package epf.nosql.schema;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * @param <T>
 */
@JsonbTypeSerializer(value = EitherArrayJsonSerializer.class)
public class StringOrArray<T> extends EitherArray<String, T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
