package epf.client.transaction;

import java.util.Map;

/**
 * 
 */
public class Response {

	/**
	 *
	 */
	private Map<String, Object> headers;
	
	/**
	 *
	 */
	private String entity;
	
	/**
	 *
	 */
	private int status;

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(final Map<String, Object> headers) {
		this.headers = headers;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}
}
