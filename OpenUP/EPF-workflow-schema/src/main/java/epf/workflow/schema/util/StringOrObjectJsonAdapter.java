package epf.workflow.schema.util;

import java.util.List;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * @param <T>
 */
public class StringOrObjectJsonAdapter<T> extends EitherJsonAdapter<StringOrObject<T>, String, T> {
	
	/**
	 * @param right
	 * @param adapterClasses
	 */
	public StringOrObjectJsonAdapter(final Class<T> right, final List<Class<? extends JsonbAdapter<?, ?>>> adapterClasses) {
		super(StringOrObject.class, String.class, right, adapterClasses);
	}
}
