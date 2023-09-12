package epf.workflow.schema.adapter;

import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;

/**
 * @author PC
 *
 */
public class BoolOrObjectAdapter implements JsonbAdapter<Object, JsonValue> {
	
	/**
	 * 
	 */
	private final Class<?> cls;
	
	/**
	 * @param cls
	 */
	public BoolOrObjectAdapter(final Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public JsonValue adaptToJson(final Object obj) throws Exception {
		return JsonUtil.toJsonValue(obj);
	}

	@Override
	public Object adaptFromJson(final JsonValue obj) throws Exception {
		switch(obj.getValueType()) {
			case ARRAY:
				break;
			case FALSE:
				return false;
			case NULL:
			case NUMBER:
				break;
			case OBJECT:
				try(Jsonb jsonb = JsonbBuilder.create()){
					return jsonb.fromJson(obj.toString(), cls);
				}
			case STRING:
				break;
			case TRUE:
				return true;
			default:
				break;
		}
		return null;
	}
}