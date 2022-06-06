package epf.api.media;

import java.util.Map;

/**
 * 
 */
public class Discriminator {

	/**
	 *
	 */
	String propertyName;
	
	/**
	 *
	 */
	Map<String, String> mapping;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(final Map<String, String> mapping) {
		this.mapping = mapping;
	}
}
