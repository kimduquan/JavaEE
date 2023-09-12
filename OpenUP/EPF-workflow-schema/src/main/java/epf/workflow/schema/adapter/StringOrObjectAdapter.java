package epf.workflow.schema.adapter;

import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;

/**
 * @author PC
 *
 */
public class StringOrObjectAdapter implements JsonbAdapter<Object, JsonValue> {
	
	/**
	 * 
	 */
	private final Class<?> cls;
	
	/**
	 * @param cls
	 */
	public StringOrObjectAdapter(final Class<?> cls) {
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
			case FALSE:
			case NULL:
			case NUMBER:
				break;
			case OBJECT:
				try(Jsonb jsonb = JsonbBuilder.create()){
					return jsonb.fromJson(obj.toString(), cls);
				}
			case STRING:
				return ((JsonString)obj).getString();
			case TRUE:
				break;
			default:
				break;
		}
		return null;
	}
}