package epf.workflow.schema.util;

import java.util.Map;

import epf.nosql.schema.Either;
import epf.util.json.ext.JsonUtil;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * @param <L>
 * @param <R>
 */
public class EitherJsonAdapter<T extends Either<L, R>, L, R> implements JsonbAdapter<T, Object> {
	
	/**
	 * 
	 */
	private transient final Class<L> left;
	/**
	 * 
	 */
	private transient final Class<R> right;
	/**
	 * 
	 */
	private transient final Class<T> clazz;
	
	private static <T> T toObject(final Object object, final Class<T> clazz) throws Exception{
		final Map<?, ?> map = (Map<?, ?>) object;
		return JsonUtil.fromMap(map, clazz);
	}
	
	/**
	 * @param clazz
	 * @param left
	 * @param right
	 */
	@SuppressWarnings("unchecked")
	public EitherJsonAdapter(final Class<?> clazz, final Class<L> left, final Class<R> right) {
		this.left = left;
		this.right = right;
		this.clazz = (Class<T>) clazz;
	}

	@Override
	public Object adaptToJson(final T obj) throws Exception {
		final Object object = obj.isLeft() ? obj.getLeft() : obj.getRight();
		return object;
	}

	@Override
	public T adaptFromJson(final Object obj) throws Exception {
		final T either = clazz.getConstructor().newInstance();
		if(obj != null) {
			if(left.isInstance(obj)) {
				either.setLeft(left.cast(obj));
			}
			else if(right.isInstance(obj)) {
				either.setRight(right.cast(obj));
			}
			else if(obj instanceof Map) {
				if(!JsonUtil.isPrimitive(left)) {
					final L leftValue = toObject(obj, left);
					either.setLeft(leftValue);
				}
				else if(!JsonUtil.isPrimitive(right)) {
					final R rightValue = toObject(obj, right);
					either.setRight(rightValue);
				}
			}
		}
		return either;
	}

}
