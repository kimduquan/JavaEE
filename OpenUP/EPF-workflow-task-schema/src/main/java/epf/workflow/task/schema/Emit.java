package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.EventProperties;
import jakarta.validation.constraints.NotBlank;

public class Emit {

	@NotBlank
	@Description("Defines the event to emit.")
	private EventProperties event;

	public EventProperties getEvent() {
		return event;
	}

	public void setEvent(EventProperties event) {
		this.event = event;
	}
}
