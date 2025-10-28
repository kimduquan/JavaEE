package epf.query.schema;

import java.util.Map;
import jakarta.validation.constraints.NotBlank;

public class NativeQuery {

	@NotBlank
	private String query;
	private Map<String, Object> parameters;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
}
