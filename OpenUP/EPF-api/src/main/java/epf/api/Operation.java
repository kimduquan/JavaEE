package epf.api;

import java.util.List;
import java.util.Map;
import epf.api.callback.Callback;
import epf.api.parameter.Parameter;
import epf.api.parameter.RequestBody;
import epf.api.response.Responses;
import epf.api.security.SecurityRequirement;
import epf.api.server.Server;

/**
 * 
 */
public class Operation extends Extensible {
	
	/**
	 *
	 */
	private List<String> tags;
	
	/**
	 *
	 */
	private String summary;
	
	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private ExternalDocumentation externalDocs;
	
	/**
	 *
	 */
	private String operationId;
	
	/**
	 *
	 */
	private List<Parameter> parameters;
	
	/**
	 *
	 */
	private RequestBody requestBody;
	
	/**
	 *
	 */
	private Responses responses;
	
	/**
	 *
	 */
	private Map<String, Callback> callbacks;
	
	/**
	 *
	 */
	private Boolean deprecated;
	
	/**
	 *
	 */
	private List<SecurityRequirement> security;
	
	/**
	 *
	 */
	private List<Server> servers;

	public List<String> getTags() {
		return tags;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ExternalDocumentation getExternalDocs() {
		return externalDocs;
	}

	public void setExternalDocs(final ExternalDocumentation externalDocs) {
		this.externalDocs = externalDocs;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(final String operationId) {
		this.operationId = operationId;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(final List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public RequestBody getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(final RequestBody requestBody) {
		this.requestBody = requestBody;
	}

	public Responses getResponses() {
		return responses;
	}

	public void setResponses(final Responses responses) {
		this.responses = responses;
	}

	public Map<String, Callback> getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(final Map<String, Callback> callbacks) {
		this.callbacks = callbacks;
	}

	public Boolean getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(final Boolean deprecated) {
		this.deprecated = deprecated;
	}

	public List<SecurityRequirement> getSecurity() {
		return security;
	}

	public void setSecurity(final List<SecurityRequirement> security) {
		this.security = security;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(final List<Server> servers) {
		this.servers = servers;
	}
}
