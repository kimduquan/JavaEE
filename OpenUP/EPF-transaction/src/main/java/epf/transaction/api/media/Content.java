package epf.transaction.api.media;

import java.util.Map;

/**
 * 
 */
public class Content {

	/**
	 *
	 */
	private Map<String, MediaType> mediaTypes;

	public Map<String, MediaType> getMediaTypes() {
		return mediaTypes;
	}

	public void setMediaTypes(final Map<String, MediaType> mediaTypes) {
		this.mediaTypes = mediaTypes;
	}
}
