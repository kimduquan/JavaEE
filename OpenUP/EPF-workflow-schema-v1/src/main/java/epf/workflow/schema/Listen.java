package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Provides a mechanism for workflows to await and react to external events, enabling event-driven behavior within workflow systems.")
public class Listen extends Task {

	public class Listen_ {
		
		@NotNull
		@JsonPropertyDescription("Configures the event(s) the workflow must listen to.")
		private EventConsumptionStrategy to;
		
		@JsonPropertyDescription("Specifies how events are read during the listen operation. Defaults to data.")
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
	
	private Listen_ listen;
	
	@JsonPropertyDescription("Configures the iterator, if any, for processing each consumed event.")
	private SubscriptionIterator foreach;

	public Listen_ getListen() {
		return listen;
	}

	public void setListen(Listen_ listen) {
		this.listen = listen;
	}

	public SubscriptionIterator getForeach() {
		return foreach;
	}

	public void setForeach(SubscriptionIterator foreach) {
		this.foreach = foreach;
	} 
}
