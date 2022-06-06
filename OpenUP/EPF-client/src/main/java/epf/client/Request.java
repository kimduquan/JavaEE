package epf.client;

import java.util.Map;

/**
 * 
 */
public class Request {

	/**
	 *
	 */
	private String target;
	
	/**
	 *
	 */
	private Map<String, Object> matrixParams;
	
	/**
	 *
	 */
	private String path;
	
	/**
	 *
	 */
	private Map<String, Object> queryParams;
	
	/**
	 *
	 */
	private Map<String, Object> headers;
	
	/**
	 *
	 */
	private String method;
	
	/**
	 *
	 */
	private String mediaType;
	
	/**
	 *
	 */
	private String entity;

	public String getTarget() {
		return target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public Map<String, Object> getMatrixParams() {
		return matrixParams;
	}

	public void setMatrixParams(final Map<String, Object> matrixParams) {
		this.matrixParams = matrixParams;
	}

	public String getPath() {
		return path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(final Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(final Map<String, Object> headers) {
		this.headers = headers;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(final String mediaType) {
		this.mediaType = mediaType;
	}
}
