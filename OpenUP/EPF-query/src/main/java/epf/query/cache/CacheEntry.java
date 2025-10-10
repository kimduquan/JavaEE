package epf.query.cache;

import epf.util.json.ext.Adapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;

public class CacheEntry {

	@JsonbTypeAdapter(Adapter.class)
	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
