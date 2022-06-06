package epf.api.response;

import java.util.Map;

import epf.api.Extensible;

/**
 * 
 */
public class Responses extends Extensible {

	/**
	 *
	 */
	private Map<String, Response> responses;
	
	/**
	 *
	 */
	private Response defaultValue;

	public Map<String, Response> getResponses() {
		return responses;
	}

	public void setResponses(final Map<String, Response> responses) {
		this.responses = responses;
	}

	public Response getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(final Response defaultValue) {
		this.defaultValue = defaultValue;
	}
}
