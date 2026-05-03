package epf.workflow.schema;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Defines the workflow's reusable components.")
public class Use {

	@JsonPropertyDescription("A name/value mapping of the workflow's reusable authentication policies.")
	private Map<String, Authentication> authentications;
	
	@JsonPropertyDescription("A name/value mapping of the workflow's reusable resource catalogs.")
	private Map<String, Catalog> catalogs;
	
	@JsonPropertyDescription("A name/value mapping of the workflow's reusable errors.")
	private Map<String, Error> errors;
	
	@JsonPropertyDescription("A list of the workflow's reusable extensions.")
	private List<Extension> extensions;
	
	@JsonPropertyDescription("A name/value mapping of the workflow's reusable tasks.")
	private Map<String, Task> functions;
	
	@JsonPropertyDescription("A name/value mapping of the workflow's reusable retry policies.")
	private Map<String, Retry> retries;
	
	@JsonPropertyDescription("A list containing the workflow's secrets.")
	private List<String> secrets;
	
	@JsonPropertyDescription("A name/value mapping of the workflow's reusable timeouts.")
	private Map<String, Timeout> timeouts;

	public Map<String, Authentication> getAuthentications() {
		return authentications;
	}

	public void setAuthentications(Map<String, Authentication> authentications) {
		this.authentications = authentications;
	}

	public Map<String, Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Map<String, Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	public Map<String, Error> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, Error> errors) {
		this.errors = errors;
	}

	public List<Extension> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<Extension> extensions) {
		this.extensions = extensions;
	}

	public Map<String, Task> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, Task> functions) {
		this.functions = functions;
	}

	public Map<String, Retry> getRetries() {
		return retries;
	}

	public void setRetries(Map<String, Retry> retries) {
		this.retries = retries;
	}

	public List<String> getSecrets() {
		return secrets;
	}

	public void setSecrets(List<String> secrets) {
		this.secrets = secrets;
	}

	public Map<String, Timeout> getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(Map<String, Timeout> timeouts) {
		this.timeouts = timeouts;
	}
}
