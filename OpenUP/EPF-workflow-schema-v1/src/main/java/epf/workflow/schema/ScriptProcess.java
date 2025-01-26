package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Enables the execution of custom scripts or code within a workflow, empowering workflows to perform specialized logic, data processing, or integration tasks by executing user-defined scripts written in various programming languages.")
public class ScriptProcess {

	@NotNull
	@Description("The language of the script to run")
	private String language;
	
	@Description("The script's code.")
	private String code;
	
	@Description("The script's resource.")
	private ExternalResource source;
	
	@Description("A list of the arguments, if any, of the script to run")
	private Map<?, ?> arguments;
	
	@Description("A key/value mapping of the environment variables, if any, to use when running the configured script process")
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

	public Map<?, ?> getArguments() {
		return arguments;
	}

	public void setArguments(Map<?, ?> arguments) {
		this.arguments = arguments;
	}

	public Map<?, ?> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<?, ?> environment) {
		this.environment = environment;
	}
}
