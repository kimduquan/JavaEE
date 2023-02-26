package epf.workflow.schema.adapter;

import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
public class EnumAdapter<T extends Enum<T>> implements JsonbAdapter<Enum<T>, JsonValue> {
	
	/**
	 * 
	 */
	private transient final Class<T> cls;
	
	/**
	 * @param cls
	 */
	public EnumAdapter(final Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public JsonValue adaptToJson(final Enum<T> obj) throws Exception {
		return JsonUtil.toJsonEnum(obj);
	}

	@Override
	public Enum<T> adaptFromJson(final JsonValue obj) throws Exception {
		return JsonUtil.asEnum(cls, obj);
	}

}
