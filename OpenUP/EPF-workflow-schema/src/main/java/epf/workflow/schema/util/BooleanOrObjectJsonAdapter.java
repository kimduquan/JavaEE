package epf.workflow.schema.util;

/**
 * @param <T>
 */
public class BooleanOrObjectJsonAdapter<T> extends EitherJsonAdapter<BooleanOrObject<T>, Boolean, T> {
	
	/**
	 * @param right
	 */
	public BooleanOrObjectJsonAdapter(final Class<T> right) {
		super(BooleanOrObject.class, Boolean.class, right);
	}
}
