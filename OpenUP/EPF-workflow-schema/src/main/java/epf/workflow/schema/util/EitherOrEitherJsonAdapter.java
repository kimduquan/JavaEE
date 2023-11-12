package epf.workflow.schema.util;

import java.util.HashMap;
import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;

/**
 * 
 */
public class EitherOrEitherJsonAdapter<L extends Either<?, ?>, R extends Either<?, ?>> implements JsonbAdapter<EitherOrEither<L, R>, Map<?, ?>> {
	
	/**
	 * 
	 */
	private final String leftKey;
	
	/**
	 * 
	 */
	private final String rightKey;
	
	/**
	 * @param leftKey
	 * @param rightKey
	 */
	public EitherOrEitherJsonAdapter(String leftKey, String rightKey) {
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	@Override
	public Map<?, ?> adaptToJson(final EitherOrEither<L, R> obj) throws Exception {
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
	public EitherOrEither<L, R> adaptFromJson(final Map<?, ?> obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
