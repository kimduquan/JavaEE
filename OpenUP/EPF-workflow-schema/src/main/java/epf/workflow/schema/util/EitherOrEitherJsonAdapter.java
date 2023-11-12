package epf.workflow.schema.util;

import java.util.HashMap;
import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * 
 */
public class EitherOrEitherJsonAdapter<T extends EitherOrEither<L, R>, L extends Either<?, ?>, R extends Either<?, ?>> implements JsonbAdapter<T, Map<?, ?>> {
	
	/**
	 * 
	 */
	private final String leftKey;
	
	/**
	 * 
	 */
	private transient final EitherJsonAdapter<?, ?, ?> leftAdapter;
	
	/**
	 * 
	 */
	private final String rightKey;
	
	/**
	 * 
	 */
	private transient final EitherJsonAdapter<?, ?, ?> rightAdapter;
	
	/**
	 * 
	 */
	private transient final Class<T> clazz;
	
	/**
	 * @param leftKey
	 * @param leftAdapter
	 * @param rightKey
	 * @param rightAdapter
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public EitherOrEitherJsonAdapter(final String leftKey, final EitherJsonAdapter<?, ?, ?> leftAdapter, final String rightKey, final EitherJsonAdapter<?, ?, ?> rightAdapter, final Class<?> clazz) {
		this.leftKey = leftKey;
		this.leftAdapter = leftAdapter;
		this.rightKey = rightKey;
		this.rightAdapter = rightAdapter;
		this.clazz = (Class<T>) clazz;
	}

	@Override
	public Map<?, ?> adaptToJson(final T obj) throws Exception {
		final Map<Object, Object> map = new HashMap<>();
		if(obj.isLeft()) {
			Object left = null;
			if(obj.getLeft().isLeft()) {
				left = obj.getLeft().getLeft();
			}
			else if(obj.getLeft().isRight()) {
				left = obj.getLeft().getRight();
			}
			map.put(leftKey, left);
		}
		else if(obj.isRight()) {
			Object right = null;
			if(obj.getRight().isLeft()) {
				right = obj.getRight().getLeft();
			}
			else if(obj.getRight().isRight()) {
				right = obj.getRight().getRight();
			}
			map.put(rightKey, right);
		}
		return map;
	}

	@Override
	public T adaptFromJson(final Map<?, ?> obj) throws Exception {
		final T either = clazz.getConstructor().newInstance();
		if(obj.containsKey(leftKey)) {
			final Object left = obj.get(leftKey);
			@SuppressWarnings("unchecked")
			final L leftEither = (L) leftAdapter.adaptFromJson(left);
			either.setLeft(leftEither);
		}
		else if(obj.containsKey(rightKey)) {
			final Object right = obj.get(rightKey);
			@SuppressWarnings("unchecked")
			final R rightEither = (R) rightAdapter.adaptFromJson(right);
			either.setRight(rightEither);
		}
		return either;
	}
}
