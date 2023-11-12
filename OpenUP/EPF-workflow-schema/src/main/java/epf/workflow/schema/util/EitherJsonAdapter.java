package epf.workflow.schema.util;

import java.util.List;
import java.util.Map;
import epf.util.ClassUtil;
import epf.util.json.ext.JsonUtil;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * @param <L>
 * @param <R>
 */
public class EitherJsonAdapter<L, R> implements JsonbAdapter<Either<L, R>, Object> {
	
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
	private transient final List<Class<? extends JsonbAdapter<?, ?>>> adapterClasses;
	
	private static <T> T toObject(final Object object, final Class<T> clazz, final List<? extends JsonbAdapter<?, ?>> adapters) throws Exception{
		final Map<?, ?> map = (Map<?, ?>) object;
		if(adapters.isEmpty()) {
			return JsonUtil.fromMap(map, clazz);
		}
		else {
			return JsonUtil.fromMap(map, clazz, new JsonbConfig().withAdapters(adapters.toArray(new JsonbAdapter<?, ?>[0])));
		}
	}
	
	/**
	 * @param left
	 * @param right
	 * @param adapterClasses
	 */
	public EitherJsonAdapter(final Class<L> left, final Class<R> right, final List<Class<? extends JsonbAdapter<?, ?>>> adapterClasses) {
		this.left = left;
		this.right = right;
		this.adapterClasses = adapterClasses;
	}

	@Override
	public Object adaptToJson(final Either<L, R> obj) throws Exception {
		final Object object = obj.isLeft() ? obj.getLeft() : obj.getRight();
		return object;
	}

	@Override
	public Either<L, R> adaptFromJson(final Object obj) throws Exception {
		final Either<L, R> either = new Either<>();
		if(obj != null) {
			if(left.isInstance(obj)) {
				either.setLeft(left.cast(obj));
			}
			else if(right.isInstance(obj)) {
				either.setRight(right.cast(obj));
			}
			else if(obj instanceof Map) {
				if(!JsonUtil.isPrimitive(left)) {
					final L leftValue = toObject(obj, left, ClassUtil.newInstances(adapterClasses));
					either.setLeft(leftValue);
				}
				else if(!JsonUtil.isPrimitive(right)) {
					final R rightValue = toObject(obj, right, ClassUtil.newInstances(adapterClasses));
					either.setRight(rightValue);
				}
			}
		}
		return either;
	}

}
