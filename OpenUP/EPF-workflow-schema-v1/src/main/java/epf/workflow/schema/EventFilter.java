package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("An event filter is a mechanism used to selectively process or handle events based on predefined criteria, such as event type, source, or specific attributes.")
public class EventFilter {

	@NotNull
	@JsonPropertyDescription("A name/value mapping of the attributes filtered events must define. Supports both regular expressions and runtime expressions.")
	private EventProperties with;
	
	@JsonPropertyDescription("A name/definition mapping of the correlations to attempt when filtering events.")
	private Map<String, Correlation> correlate;

	public EventProperties getWith() {
		return with;
	}

	public void setWith(EventProperties with) {
		this.with = with;
	}

	public Map<String, Correlation> getCorrelate() {
		return correlate;
	}

	public void setCorrelate(Map<String, Correlation> correlate) {
		this.correlate = correlate;
	}
}
