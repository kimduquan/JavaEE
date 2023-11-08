package epf.workflow.schema.adapter;

import java.util.HashMap;
import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.Either;

/**
 * @author PC
 *
 */
public class TransitionOrEndAdapter implements JsonbAdapter<Either<Either<String, TransitionDefinition>, Either<Boolean, EndDefinition>>, Map<String, Object>> {
	
	/**
	 * 
	 */
	private final TransitionDefinitionAdapter transitionAdapter = new TransitionDefinitionAdapter();
	
	/**
	 * 
	 */
	private final EndDefinitionAdapter endAdapter = new EndDefinitionAdapter();

	@Override
	public Map<String, Object> adaptToJson(final Either<Either<String, TransitionDefinition>, Either<Boolean, EndDefinition>> obj) throws Exception {
		final Map<String, Object> map = new HashMap<>();
		if(obj.isLeft()) {
			Object left = null;
			if(obj.getLeft().isLeft()) {
				left = obj.getLeft().getLeft();
			}
			else if(obj.getLeft().isRight()) {
				left = obj.getLeft().getRight();
			}
			map.put("transition", left);
		}
		if(obj.isRight()) {
			Object right = null;
			if(obj.getRight().isLeft()) {
				right = obj.getRight().getLeft();
			}
			else if(obj.getRight().isRight()) {
				right = obj.getRight().getRight();
			}
			map.put("end", right);
		}
		return map;
	}

	@Override
	public Either<Either<String, TransitionDefinition>, Either<Boolean, EndDefinition>> adaptFromJson(final Map<String, Object> obj) throws Exception {
		final Either<Either<String, TransitionDefinition>, Either<Boolean, EndDefinition>> either = new Either<>();
		final Object transition = obj.get("transition");
		if(transition != null) {
			either.setLeft(transitionAdapter.adaptFromJson(transition));
		}
		final Object end = obj.get("end");
		if(end != null) {
			either.setRight(endAdapter.adaptFromJson(end));
		}
		return either;
	}
}
