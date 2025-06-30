package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotBlank;

@Description("Enables the execution of custom scripts or code within a workflow, empowering workflows to perform specialized logic, data processing, or integration tasks by executing user-defined scripts written in various programming languages.")
public class ScriptProcess {
	
	public static final String JS = "js";
	public static final String PYTHON = "python";

	@NotBlank
	@Description("The language of the script to run.")
	private String language;
	
	@Description("The script's code.")
	private String code;
	
	@Description("The script's resource.")
	private ExternalResource source;
	
	@Description("A list of the arguments, if any, of the script to run")
	private Map<String, Object> arguments;
	
	@Description("A key/value mapping of the environment variables, if any, to use when running the configured script process")
	private Map<String, String> environment;

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

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, String> environment) {
		this.environment = environment;
	}
}
