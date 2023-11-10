package epf.workflow.schema.util;

import java.util.Map;
import epf.util.json.ext.JsonUtil;
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
	 * @param left
	 * @param right
	 */
	public EitherJsonAdapter(final Class<L> left, final Class<R> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object adaptToJson(final Either<L, R> obj) throws Exception {
		if(obj.isLeft()) {
			return obj.getLeft();
		}
		return obj.getRight();
	}

	@Override
	public Either<L, R> adaptFromJson(final Object obj) throws Exception {
		final Either<L, R> either = new Either<>();
		if(left.isInstance(obj)) {
			either.setLeft(left.cast(obj));
		}
		else if(right.isInstance(obj)) {
			either.setRight(right.cast(obj));
		}
		else if(obj instanceof Map) {
			if(!JsonUtil.isPrimitive(left)) {
				final Map<?, ?> map = (Map<?, ?>) obj;
				final L leftValue = JsonUtil.fromMap(map, left);
				either.setLeft(leftValue);
			}
			else if(!JsonUtil.isPrimitive(right)) {
				final Map<?, ?> map = (Map<?, ?>) obj;
				final R rightValue = JsonUtil.fromMap(map, right);
				either.setRight(rightValue);
			}
		}
		return either;
	}

}
