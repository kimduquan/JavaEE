package epf.workflow.schema;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Enables the execution of external processes encapsulated within a containerized environment, allowing workflows to interact with and execute complex operations using containerized applications, scripts, or commands.")
public class ContainerProcess {

	@NotNull
	@JsonPropertyDescription("The name of the container image to run")
	private String image;
	
	@JsonPropertyDescription("A runtime expression, if any, used to give specific name to the container.")
	private String name;
	
	@JsonPropertyDescription("The command, if any, to execute on the container")
	private String command;
	
	@JsonPropertyDescription("The container's port mappings, if any")
	private Map<?, ?> ports;
	
	@JsonPropertyDescription("The container's volume mappings, if any")
	private Map<?, ?> volumes;
	
	@JsonPropertyDescription("A key/value mapping of the environment variables, if any, to use when running the configured process")
	private Map<?, ?> environment;
	
	@JsonPropertyDescription("A runtime expression, if any, passed as standard input to the command or default container CMD")
	private String stdin;
	
	@JsonPropertyDescription("A list of the arguments, if any, passed as argv to the command or default container CMD")
	private List<String> arguments;
	
	@JsonPropertyDescription("An object used to configure the container's lifetime.")
	private ContainerLifetime lifetime;
	
	@JsonPropertyDescription("Policy that controls how the container's image should be pulled from the registry. Defaults to ifNotPresent")
	private String pullPolicy = "ifNotPresent";

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<?, ?> getPorts() {
		return ports;
	}

	public void setPorts(Map<?, ?> ports) {
		this.ports = ports;
	}

	public Map<?, ?> getVolumes() {
		return volumes;
	}

	public void setVolumes(Map<?, ?> volumes) {
		this.volumes = volumes;
	}

	public Map<?, ?> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<?, ?> environment) {
		this.environment = environment;
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

	public ContainerLifetime getLifetime() {
		return lifetime;
	}

	public void setLifetime(ContainerLifetime lifetime) {
		this.lifetime = lifetime;
	}

	public String getPullPolicy() {
		return pullPolicy;
	}

	public void setPullPolicy(String pullPolicy) {
		this.pullPolicy = pullPolicy;
	}
}
