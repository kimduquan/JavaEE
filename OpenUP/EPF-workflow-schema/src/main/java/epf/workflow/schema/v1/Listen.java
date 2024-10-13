package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Provides a mechanism for workflows to await and react to external events, enabling event-driven behavior within workflow systems.")
public class Listen {
	
	public class _Listen {

		@NotNull
		@Description("Configures the event(s) the workflow must listen to.")
		private EventProperties to;

		public EventProperties getTo() {
			return to;
		}

		public void setTo(EventProperties to) {
			this.to = to;
		}
	}
	
	private _Listen listen;

	public _Listen getListen() {
		return listen;
	}

	public void setListen(_Listen listen) {
		this.listen = listen;
	}
}
