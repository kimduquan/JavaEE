package epf.api.parameter;

import epf.api.Extensible;
import epf.api.media.Content;

/**
 * 
 */
public class RequestBody extends Extensible {
	
	/**
	 *
	 */
	private String ref;

	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private Content content;
	
	/**
	 *
	 */
	private Boolean required;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(final Content content) {
		this.content = content;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(final Boolean required) {
		this.required = required;
	}
}
