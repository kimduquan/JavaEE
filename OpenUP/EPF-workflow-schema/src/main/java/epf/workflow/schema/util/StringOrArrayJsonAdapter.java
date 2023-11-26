package epf.workflow.schema.util;

/**
 * @param <T>
 */
public class StringOrArrayJsonAdapter<T> extends EitherArrayJsonAdapter<StringOrArray<T>, String, T> {
	
	/**
	 * @param right
	 */
	public StringOrArrayJsonAdapter(final Class<T> right) {
		super(StringOrArray.class, String.class, right);
	}
}
