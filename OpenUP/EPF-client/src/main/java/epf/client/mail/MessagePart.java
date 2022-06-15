package epf.client.mail;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public class MessagePart {
	
	/**
	 *
	 */
	private Map<String, String> headers;
	
	/**
	 *
	 */
	private List<String> headerLines;

	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private String text;
	
	/**
	 *
	 */
	private MessageMultipart content;

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(final Map<String, String> headers) {
		this.headers = headers;
	}

	public List<String> getHeaderLines() {
		return headerLines;
	}

	public void setHeaderLines(final List<String> headerLines) {
		this.headerLines = headerLines;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public MessageMultipart getContent() {
		return content;
	}

	public void setContent(final MessageMultipart content) {
		this.content = content;
	}
}
