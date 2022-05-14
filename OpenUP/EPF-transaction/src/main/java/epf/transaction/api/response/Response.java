package epf.transaction.api.response;

import java.util.Map;
import epf.transaction.api.header.Header;
import epf.transaction.api.link.Link;
import epf.transaction.api.media.Content;

/**
 * 
 */
public class Response {
	
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
	private Map<String, Header> headers;
	
	/**
	 *
	 */
	private Content content;
	
	/**
	 *
	 */
	private Map<String, Link> links;

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

	public Map<String, Header> getHeaders() {
		return headers;
	}

	public void setHeaders(final Map<String, Header> headers) {
		this.headers = headers;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(final Content content) {
		this.content = content;
	}

	public Map<String, Link> getLinks() {
		return links;
	}

	public void setLinks(final Map<String, Link> links) {
		this.links = links;
	}
}
