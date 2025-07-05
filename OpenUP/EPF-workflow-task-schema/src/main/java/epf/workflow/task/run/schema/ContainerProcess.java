package epf.workflow.task.run.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotBlank;

@Description("Enables the execution of external processes encapsulated within a containerized environment, allowing workflows to interact with and execute complex operations using containerized applications, scripts, or commands.")
public class ContainerProcess {

	@NotBlank
	@Description("The name of the container image to run")
	private String image;
	
	@Description("A runtime expression, if any, used to give specific name to the container.")
	private String name;
	
	@Description("The command, if any, to execute on the container")
	private String command;
	
	@Description("The container's port mappings, if any")
	private Map<String, String> ports;
	
	@Description("The container's volume mappings, if any")
	private Map<String, String> volumes;
	
	@Description("A key/value mapping of the environment variables, if any, to use when running the configured process")
	private Map<String, String> environment;
	
	@Description("An object used to configure the container's lifetime.")
	private ContainerLifetime lifetime;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<String, String> getPorts() {
		return ports;
	}

	public void setPorts(Map<String, String> ports) {
		this.ports = ports;
	}

	public Map<String, String> getVolumes() {
		return volumes;
	}

	public void setVolumes(Map<String, String> volumes) {
		this.volumes = volumes;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, String> environment) {
		this.environment = environment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContainerLifetime getLifetime() {
		return lifetime;
	}

	public void setLifetime(ContainerLifetime lifetime) {
		this.lifetime = lifetime;
	}
}
