package epf.util.json.ext.adapter;

import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;

/**
 * @author PC
 *
 */
public class StringOrObjectAdapter<T> implements JsonbAdapter<Object, Object> {
	
	/**
	 * 
	 */
	private final Class<T> cls;
	
	/**
	 * @param cls
	 */
	public StringOrObjectAdapter(final Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public Object adaptToJson(final Object obj) throws Exception {
		Object object = null;
		if(obj instanceof String || cls.isInstance(obj)) {
			object = obj;
		}
		return object;
	}

	@Override
	public Object adaptFromJson(final Object obj) throws Exception {
		Object object = null;
		if(obj instanceof String) {
			object = obj;
		}
		else if(obj instanceof Map) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> map = (Map<String, Object>) obj;
			object = JsonUtil.fromMap(map, cls);
		}
		return object;
	}
}