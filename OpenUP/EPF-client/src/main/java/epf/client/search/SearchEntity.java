package epf.client.search;

import java.util.Map;

/**
 * 
 */
public class SearchEntity {

	/**
	 *
	 */
	private String schema;
	/**
	 *
	 */
	private String name;
	/**
	 *
	 */
	private Map<String, Object> attributes;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(final String schema) {
		this.schema = schema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(final Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
