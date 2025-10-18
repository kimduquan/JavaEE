package epf.query.cache;

import epf.util.json.ext.Adapter;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.json.bind.annotation.JsonbTypeAdapter;

@RegisterForReflection
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
