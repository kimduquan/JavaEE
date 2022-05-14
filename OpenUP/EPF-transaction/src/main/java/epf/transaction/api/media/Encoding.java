package epf.transaction.api.media;

import java.util.Map;
import epf.transaction.api.Extensible;
import epf.transaction.api.header.Header;

/**
 * 
 */
public class Encoding extends Extensible {

	/**
	 * 
	 */
	enum Style {
		form, 
		spaceDelimited, 
		pipeDelimited, 
		deepObject;
	};
	
	/**
	 *
	 */
	private String contentType;
	
	/**
	 *
	 */
	private Map<String, Header> headers;
	
	/**
	 *
	 */
	private Style style;
	
	/**
	 *
	 */
	private Boolean explode;
	
	/**
	 *
	 */
	private Boolean allowReserved;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	public Map<String, Header> getHeaders() {
		return headers;
	}

	public void setHeaders(final Map<String, Header> headers) {
		this.headers = headers;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(final Style style) {
		this.style = style;
	}

	public Boolean getExplode() {
		return explode;
	}

	public void setExplode(final Boolean explode) {
		this.explode = explode;
	}

	public Boolean getAllowReserved() {
		return allowReserved;
	}

	public void setAllowReserved(final Boolean allowReserved) {
		this.allowReserved = allowReserved;
	}
}
