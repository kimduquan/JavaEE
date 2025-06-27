package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.EventConsumptionStrategy;
import jakarta.validation.constraints.NotNull;

@Description("Provides a mechanism for workflows to await and react to external events, enabling event-driven behavior within workflow systems.")
public class Listen {

	@NotNull
	@Description("Configures the event(s) the workflow must listen to.")
	private EventConsumptionStrategy to;
	
	@Description("Specifies how events are read during the listen operation.")
	@DefaultValue("data")
	private String read = "data";

	public EventConsumptionStrategy getTo() {
		return to;
	}

	public void setTo(EventConsumptionStrategy to) {
		this.to = to;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}
}