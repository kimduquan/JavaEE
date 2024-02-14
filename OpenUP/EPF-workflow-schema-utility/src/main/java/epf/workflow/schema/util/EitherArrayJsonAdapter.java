package epf.workflow.schema.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import epf.nosql.schema.EitherArray;
import epf.util.json.ext.JsonUtil;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * @param <L>
 * @param <R>
 */
public class EitherArrayJsonAdapter<T extends EitherArray<L, R>, L, R> implements JsonbAdapter<T, Object> {
	
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
	public EitherArrayJsonAdapter(final Class<?> clazz, final Class<L> left, final Class<R> right) {
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
		if(obj instanceof List) {
			final List<?> list = (List<?>) obj;
			final List<R> eitherRight = new ArrayList<>();
			for(Object ele : list) {
				if(ele == null) {
					eitherRight.add(null);
				}
				else if(right.isInstance(ele)) {
					eitherRight.add(right.cast(ele));
				}
				else {
					final R rightValue = toObject(ele, right);
					eitherRight.add(rightValue);
				}
			}
			either.setRight(eitherRight);
		}
		else if(left.isInstance(obj)) {
			either.setLeft(left.cast(obj));
		}
		else if(obj instanceof Map && !JsonUtil.isPrimitive(left)) {
			final L leftValue = toObject(obj, left);
			either.setLeft(leftValue);
		}
		return either;
	}

}
