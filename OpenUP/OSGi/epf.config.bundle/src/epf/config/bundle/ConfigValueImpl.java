package epf.config.bundle;

import java.util.Map;

public class ConfigValueImpl implements epf.config.ConfigValue {
	
	/**
	 * 
	 */
	private transient final Map.Entry<Object, Object> entry;
	
	/**
	 * @param entry
	 */
	public ConfigValueImpl(final Map.Entry<Object, Object> entry) {
		this.entry = entry;
	}

	@Override
	public String getName() {
		return entry.getKey().toString();
	}

	@Override
	public String getValue() {
		return entry.getValue().toString();
	}

}
