package epf.workflow.schema.adapter;

import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;

/**
 * @author PC
 *
 */
public class TransitionOrEndAdapter implements JsonbAdapter<Object, Map<String, Object>> {
	
	/**
	 * 
	 */
	private final TransitionDefinitionAdapter transitionAdapter = new TransitionDefinitionAdapter();
	
	/**
	 * 
	 */
	private final EndDefinitionAdapter endAdapter = new EndDefinitionAdapter();

	@Override
	public Map<String, Object> adaptToJson(final Object obj) throws Exception {
		return JsonUtil.toMap(obj);
	}

	@Override
	public Object adaptFromJson(final Map<String, Object> obj) throws Exception {
		@SuppressWarnings("unchecked")
		final Map<String, Object> transition = (Map<String, Object>) obj.get("transition");
		if(transition != null) {
			return transitionAdapter.adaptFromJson(transition);
		}
		@SuppressWarnings("unchecked")
		final Map<String, Object> end = (Map<String, Object>) obj.get("end");
		if(end != null) {
			return endAdapter.adaptFromJson(end);
		}
		return null;
	}
}
