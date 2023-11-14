package epf.workflow.schema.util;

import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * @param <T>
 */
@JsonbTypeSerializer(value = EitherJsonSerializer.class)
public class NumberOrObject<T> extends Either<Number, T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
