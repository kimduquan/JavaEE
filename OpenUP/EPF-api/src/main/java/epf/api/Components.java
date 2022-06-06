package epf.api;

import java.util.Map;

import epf.api.callback.Callback;
import epf.api.example.Example;
import epf.api.header.Header;
import epf.api.link.Link;
import epf.api.media.Schema;
import epf.api.parameter.Parameter;
import epf.api.parameter.RequestBody;
import epf.api.response.Response;
import epf.api.security.SecurityScheme;

/**
 * 
 */
public class Components extends Extensible {

	/**
	 *
	 */
	private Map<String, Schema> schemas;
	
	/**
	 *
	 */
	private Map<String, Response> responses;
	
	/**
	 *
	 */
	private Map<String, Parameter> parameters;
	
	/**
	 *
	 */
	private Map<String, Example> examples;
	
	/**
	 *
	 */
	private Map<String, RequestBody> requestBodies;
	
	/**
	 *
	 */
	private Map<String, Header> headers;
	
	/**
	 *
	 */
	private Map<String, SecurityScheme> securitySchemes;
	
	/**
	 *
	 */
	private Map<String, Link> links;
	
	/**
	 *
	 */
	private Map<String, Callback> callbacks;

	public Map<String, Schema> getSchemas() {
		return schemas;
	}

	public void setSchemas(final Map<String, Schema> schemas) {
		this.schemas = schemas;
	}

	public Map<String, Response> getResponses() {
		return responses;
	}

	public void setResponses(final Map<String, Response> responses) {
		this.responses = responses;
	}

	public Map<String, Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Parameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Example> getExamples() {
		return examples;
	}

	public void setExamples(final Map<String, Example> examples) {
		this.examples = examples;
	}

	public Map<String, RequestBody> getRequestBodies() {
		return requestBodies;
	}

	public void setRequestBodies(final Map<String, RequestBody> requestBodies) {
		this.requestBodies = requestBodies;
	}

	public Map<String, Header> getHeaders() {
		return headers;
	}

	public void setHeaders(final Map<String, Header> headers) {
		this.headers = headers;
	}

	public Map<String, SecurityScheme> getSecuritySchemes() {
		return securitySchemes;
	}

	public void setSecuritySchemes(final Map<String, SecurityScheme> securitySchemes) {
		this.securitySchemes = securitySchemes;
	}

	public Map<String, Link> getLinks() {
		return links;
	}

	public void setLinks(final Map<String, Link> links) {
		this.links = links;
	}

	public Map<String, Callback> getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(final Map<String, Callback> callbacks) {
		this.callbacks = callbacks;
	}
}
