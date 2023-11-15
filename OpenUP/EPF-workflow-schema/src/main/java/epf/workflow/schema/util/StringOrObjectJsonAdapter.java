package epf.workflow.schema.util;

/**
 * @param <T>
 */
public class StringOrObjectJsonAdapter<T> extends EitherJsonAdapter<StringOrObject<T>, String, T> {
	
	/**
	 * @param right
	 */
	public StringOrObjectJsonAdapter(final Class<T> right) {
		super(StringOrObject.class, String.class, right);
	}
}
