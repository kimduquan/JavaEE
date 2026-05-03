package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Allows workflows to publish events to event brokers or messaging systems, facilitating communication and coordination between different components and services. With the Emit task, workflows can seamlessly integrate with event-driven architectures, enabling real-time processing, event-driven decision-making, and reactive behavior based on incoming events.")
public class Emit extends Task {
	
	public class Emit_ {

		@NotNull
		@JsonPropertyDescription("Defines the event to emit.")
		private EventProperties event;

		public EventProperties getEvent() {
			return event;
		}

		public void setEvent(EventProperties event) {
			this.event = event;
		}
	}
	
	private Emit_ emit;

	public Emit_ getEmit() {
		return emit;
	}

	public void setEmit(Emit_ emit) {
		this.emit = emit;
	}
}
