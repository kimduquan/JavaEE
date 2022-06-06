package epf.api;

/**
 * 
 */
public class ExternalDocumentation extends Extensible {

	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private String url;

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}
