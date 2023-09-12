package epf.workflow.schema.adapter;

import jakarta.json.bind.adapter.JsonbAdapter;
import epf.workflow.schema.util.EnumUtil;

/**
 * @author PC
 *
 */
public class EnumAdapter<T extends Enum<T>> implements JsonbAdapter<Enum<T>, String> {
	
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
	public String adaptToJson(final Enum<T> obj) throws Exception {
		return obj.name();
	}

	@Override
	public Enum<T> adaptFromJson(final String obj) throws Exception {
		return EnumUtil.valueOf(cls, obj);
	}

}
