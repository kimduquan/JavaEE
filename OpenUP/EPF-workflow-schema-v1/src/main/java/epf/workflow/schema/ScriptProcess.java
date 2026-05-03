package epf.workflow.schema;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Enables the execution of custom scripts or code within a workflow, empowering workflows to perform specialized logic, data processing, or integration tasks by executing user-defined scripts written in various programming languages.")
public class ScriptProcess {

	@NotNull
	@JsonPropertyDescription("The language of the script to run. Supported values are: js and python.")
	private String language;
	
	@JsonPropertyDescription("The script's code. Required if source has not been set.")
	private String code;
	
	@JsonPropertyDescription("The script's resource. Required if code has not been set.")
	private ExternalResource source;
	
	@JsonPropertyDescription("A runtime expression, if any, to the script as standard input (stdin).")
	private String stdin;
	
	@JsonPropertyDescription("A list of the arguments, if any, to the script as argv")
	private List<String> arguments;
	
	@JsonPropertyDescription("A key/value mapping of the environment variables, if any, to use when running the configured script process")
	private Map<?, ?> environment;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ExternalResource getSource() {
		return source;
	}

	public void setSource(ExternalResource source) {
		this.source = source;
	}

	public String getStdin() {
		return stdin;
	}

	public void setStdin(String stdin) {
		this.stdin = stdin;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	public Map<?, ?> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<?, ?> environment) {
		this.environment = environment;
	}
}
