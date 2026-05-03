package epf.workflow.schema;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;

@JsonClassDescription("Represents the configuration of an event consumption strategy.")
public class EventConsumptionStrategy {

	@JsonPropertyDescription("Configures the workflow to wait for all defined events before resuming execution. Required if any and one have not been set.")
	private List<EventFilter> all;
	
	@JsonPropertyDescription("Configures the workflow to wait for any of the defined events before resuming execution. Required if all and one have not been set. If empty, listens to all incoming events")
	private List<EventFilter> any;
	
	@JsonPropertyDescription("Configures the workflow to wait for the defined event before resuming execution. Required if all and any have not been set.")
	private EventFilter one;
	
	@JsonPropertyDescription("Configures the runtime expression condition or the events that must be consumed to stop listening. Only applies if any has been set, otherwise ignored. If not present, once any event is received, it proceeds to the next task.")
	private Either<String, EventConsumptionStrategy> until;

	public List<EventFilter> getAll() {
		return all;
	}

	public void setAll(List<EventFilter> all) {
		this.all = all;
	}

	public List<EventFilter> getAny() {
		return any;
	}

	public void setAny(List<EventFilter> any) {
		this.any = any;
	}

	public EventFilter getOne() {
		return one;
	}

	public void setOne(EventFilter one) {
		this.one = one;
	}

	public Either<String, EventConsumptionStrategy> getUntil() {
		return until;
	}

	public void setUntil(Either<String, EventConsumptionStrategy> until) {
		this.until = until;
	}
}
