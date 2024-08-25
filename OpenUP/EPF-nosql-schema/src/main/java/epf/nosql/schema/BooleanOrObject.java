package epf.nosql.schema;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * @param <T>
 */
@JsonbTypeSerializer(value = EitherJsonSerializer.class)
public class BooleanOrObject<T> extends Either<Boolean, T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
