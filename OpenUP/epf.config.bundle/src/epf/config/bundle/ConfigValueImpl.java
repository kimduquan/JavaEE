package epf.config.bundle;

import java.util.Map;

public class ConfigValueImpl implements epf.config.ConfigValue {
	
	/**
	 * 
	 */
	private transient final Map.Entry<String, String> entry;
	
	/**
	 * @param entry
	 */
	public ConfigValueImpl(final Map.Entry<String, String> entry) {
		this.entry = entry;
	}

	@Override
	public String getName() {
		return entry.getKey();
	}

	@Override
	public String getValue() {
		return entry.getValue();
	}

}
