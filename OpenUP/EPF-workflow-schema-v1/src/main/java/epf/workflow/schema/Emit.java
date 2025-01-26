package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Allows workflows to publish events to event brokers or messaging systems, facilitating communication and coordination between different components and services. With the Emit task, workflows can seamlessly integrate with event-driven architectures, enabling real-time processing, event-driven decision-making, and reactive behavior based on incoming events.")
public class Emit {

	public class EmitEvent {
		
		@NotNull
		@Description("Defines the event to emit.")
		private EventProperties event;

		public EventProperties getEvent() {
			return event;
		}

		public void setEvent(EventProperties event) {
			this.event = event;
		}
	}
	
	private EmitEvent emit;

	public EmitEvent getEmit() {
		return emit;
	}

	public void setEmit(EmitEvent emit) {
		this.emit = emit;
	}
}
