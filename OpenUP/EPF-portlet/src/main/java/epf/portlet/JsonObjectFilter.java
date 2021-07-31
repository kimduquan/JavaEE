/**
 * 
 */
package epf.portlet;

import javax.json.JsonObject;

/**
 * @author PC
 *
 */
public class JsonObjectFilter {
	
	/**
	 * 
	 */
	private transient final String name;
	
	/**
	 * 
	 */
	private transient boolean include;
	
	/**
	 * 
	 */
	private transient String value;
	
	/**
	 * @param name
	 */
	public JsonObjectFilter(final String name) {
		this.name = name;
		include = true;
	}

	/**
	 * @param object
	 * @return
	 */
	public boolean filter(final JsonObject object) {
		boolean result = true;
		if(this.value != null) {
			final String value = JsonUtil.toString(object.get(name));
			result = this.value.equals(value);
		}
		return result && include;
	}

	/**
	 * @return the include
	 */
	public boolean isInclude() {
		return include;
	}

	/**
	 * @param include the include to set
	 */
	public void setInclude(final boolean include) {
		this.include = include;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}
}
