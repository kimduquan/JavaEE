package epf.nosql.schema;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * @param <T>
 */
@JsonbTypeSerializer(value = EitherJsonSerializer.class)
public class StringOrObject<T> extends Either<String, T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
