package epf.workflow.schema.adapter;

import java.util.HashMap;
import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.Either;
import epf.workflow.schema.util.StringOrObject;

/**
 * @author PC
 *
 */
public class TransitionOrEndAdapter implements JsonbAdapter<Either<StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>>, Map<String, Object>> {
	
	/**
	 * 
	 */
	private final TransitionDefinitionAdapter transitionAdapter = new TransitionDefinitionAdapter();
	
	/**
	 * 
	 */
	private final EndDefinitionAdapter endAdapter = new EndDefinitionAdapter();

	@Override
	public Map<String, Object> adaptToJson(final Either<StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> obj) throws Exception {
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
	public Either<StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> adaptFromJson(final Map<String, Object> obj) throws Exception {
		final Either<StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> either = new Either<>();
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
