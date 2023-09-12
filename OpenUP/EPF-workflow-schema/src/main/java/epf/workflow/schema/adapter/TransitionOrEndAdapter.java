package epf.workflow.schema.adapter;

import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;

/**
 * @author PC
 *
 */
public class TransitionOrEndAdapter implements JsonbAdapter<Object, JsonValue> {
	
	/**
	 * 
	 */
	private final TransitionDefinitionAdapter transitionAdapter = new TransitionDefinitionAdapter();
	
	/**
	 * 
	 */
	private final EndDefinitionAdapter endAdapter = new EndDefinitionAdapter();

	@Override
	public JsonValue adaptToJson(final Object obj) throws Exception {
		return JsonUtil.toJsonValue(obj);
	}

	@Override
	public Object adaptFromJson(final JsonValue obj) throws Exception {
		switch(obj.getValueType()) {
			case ARRAY:
			case FALSE:
			case NULL:
			case NUMBER:
				break;
			case OBJECT:
				final JsonValue transition = obj.asJsonObject().get("transition");
				if(transition != null) {
					return transitionAdapter.adaptFromJson(transition);
				}
				final JsonValue end = obj.asJsonObject().get("end");
				if(end != null) {
					return endAdapter.adaptFromJson(end);
				}
				break;
			case STRING:
			case TRUE:
				break;
			default:
				break;
		}
		return null;
	}
}
