package epf.transaction.api.link;

import java.util.Map;
import epf.transaction.api.Extensible;
import epf.transaction.api.server.Server;

/**
 * 
 */
public class Link extends Extensible {

	/**
	 *
	 */
	private String ref;
	
	/**
	 *
	 */
	private Server server;
	
	/**
	 *
	 */
	private String operationRef;
	
	/**
	 *
	 */
	private Object requestBody;
	
	/**
	 *
	 */
	private String operationId;
	
	/**
	 *
	 */
	private Map<String, Object> parameters;
	
	/**
	 *
	 */
	private String description;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(final Server server) {
		this.server = server;
	}

	public String getOperationRef() {
		return operationRef;
	}

	public void setOperationRef(final String operationRef) {
		this.operationRef = operationRef;
	}

	public Object getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(final Object requestBody) {
		this.requestBody = requestBody;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(final String operationId) {
		this.operationId = operationId;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
