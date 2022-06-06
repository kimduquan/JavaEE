package epf.api.server;

import epf.api.Extensible;

/**
 * 
 */
public class Server extends Extensible {

	/**
	 *
	 */
	private String url;
	
	/**
	 *
	 */
	private String description;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
