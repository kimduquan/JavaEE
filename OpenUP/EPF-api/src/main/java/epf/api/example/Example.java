package epf.api.example;

import epf.api.Extensible;

/**
 * 
 */
public class Example extends Extensible {

	/**
	 *
	 */
	private String ref;
	
	/**
	 *
	 */
	private String summary;
	
	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private Object value;
	
	/**
	 *
	 */
	private String externalValue;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public String getExternalValue() {
		return externalValue;
	}

	public void setExternalValue(final String externalValue) {
		this.externalValue = externalValue;
	}
}
