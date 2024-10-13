package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;

@Description("Defines the workflow's reusable components.")
public class Use {

	@Description("A name/value mapping of the workflow's reusable authentication policies.")
	private Map<String, Authentication> authentications;
	
	@Description("A name/value mapping of the workflow's reusable errors.")
	private Map<String, Error> errors;
	
	@Description("A list of the workflow's reusable extensions.")
	private Map<String, Extension> extensions;
	
	@Description("A name/value mapping of the workflow's reusable tasks.")
	private Map<String, Task> functions;
	
	@Description("A name/value mapping of the workflow's reusable retry policies.")
	private Map<String, RetryPolicy> retries;
	
	@Description("A list containing the workflow's secrets.")
	private String[] secrets;
	
	@Description("A name/value mapping of the workflow's reusable timeouts.")
	private Map<String, Timeout> timeouts;

	public Map<String, Authentication> getAuthentications() {
		return authentications;
	}

	public void setAuthentications(Map<String, Authentication> authentications) {
		this.authentications = authentications;
	}

	public Map<String, Error> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, Error> errors) {
		this.errors = errors;
	}

	public Map<String, Extension> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<String, Extension> extensions) {
		this.extensions = extensions;
	}

	public Map<String, Task> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, Task> functions) {
		this.functions = functions;
	}

	public Map<String, RetryPolicy> getRetries() {
		return retries;
	}

	public void setRetries(Map<String, RetryPolicy> retries) {
		this.retries = retries;
	}

	public String[] getSecrets() {
		return secrets;
	}

	public void setSecrets(String[] secrets) {
		this.secrets = secrets;
	}

	public Map<String, Timeout> getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(Map<String, Timeout> timeouts) {
		this.timeouts = timeouts;
	}
}
