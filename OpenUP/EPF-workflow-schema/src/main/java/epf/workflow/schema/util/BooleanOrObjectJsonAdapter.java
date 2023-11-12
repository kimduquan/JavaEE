package epf.workflow.schema.util;

import java.util.List;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * @param <T>
 */
public class BooleanOrObjectJsonAdapter<T> extends EitherJsonAdapter<BooleanOrObject<T>, Boolean, T> {
	
	/**
	 * @param right
	 * @param adapterClasses
	 */
	public BooleanOrObjectJsonAdapter(final Class<T> right, final List<Class<? extends JsonbAdapter<?, ?>>> adapterClasses) {
		super(BooleanOrObject.class, Boolean.class, right, adapterClasses);
	}
}
