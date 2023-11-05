package epf.util.json.ext.adapter;

import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;

/**
 * @author PC
 *
 */
public class BoolOrObjectAdapter<T> implements JsonbAdapter<Object, Object> {
	
	/**
	 * 
	 */
	private final Class<T> cls;
	
	/**
	 * @param cls
	 */
	public BoolOrObjectAdapter(final Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public Object adaptToJson(final Object obj) throws Exception {
		Object object = null;
		if(obj instanceof Boolean || cls.isInstance(obj)) {
			object = obj;
		}
		return object;
	}

	@Override
	public Object adaptFromJson(final Object obj) throws Exception {
		Object object = null;
		if(obj instanceof Boolean) {
			object = obj;
		}
		else if(obj instanceof Map) {
			final Map<?, ?> map = (Map<?, ?>) obj;
			object = JsonUtil.fromMap(map, cls);
		}
		return object;
	}
}