package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Enables the execution of external processes encapsulated within a containerized environment, allowing workflows to interact with and execute complex operations using containerized applications, scripts, or commands.")
public class ContainerProcess {

	@NotNull
	@Description("The name of the container image to run")
	private String image;
	
	@Description("The command, if any, to execute on the container")
	private String command;
	
	@Description("The container's port mappings, if any")
	private Map<?, ?> ports;
	
	@Description("The container's volume mappings, if any")
	private Map<?, ?> volumes;
	
	@Description("A key/value mapping of the environment variables, if any, to use when running the configured process")
	private Map<?, ?> environment;

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
}
