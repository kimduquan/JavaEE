package epf.workflow.schema.util;

import java.util.List;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * @param <T>
 * @param <L>
 * @param <R>
 */
public class PrimitiveOrObjectJsonAdapter<T extends Either<L, R>, L, R> implements JsonbAdapter<T, Object> {
	
	/**
	 * 
	 */
	private transient final EitherJsonAdapter<L, R> either;
	
	/**
	 * 
	 */
	private transient final Class<? extends T> clazz;
	
	/**
	 * @param clazz
	 * @param left
	 * @param right
	 * @param adapterClasses
	 */
	@SuppressWarnings("unchecked")
	public PrimitiveOrObjectJsonAdapter(final Class<?> clazz, final Class<L> left, final Class<R> right, final List<Class<? extends JsonbAdapter<?, ?>>> adapterClasses) {
		either = new EitherJsonAdapter<>(left, right, adapterClasses);
		this.clazz = (Class<? extends T>) clazz;
	}

	@Override
	public Object adaptToJson(final T obj) throws Exception {
		return either.adaptToJson(obj);
	}

	@Override
	public T adaptFromJson(final Object obj) throws Exception {
		final T object = clazz.getConstructor().newInstance();
		object.copy(either.adaptFromJson(obj));
		return object;
	}

}
