package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;

@Description("Represents the configuration of an event consumption strategy.")
public class EventConsumptionStrategy {

	@Description("Configures the workflow to wait for all defined events before resuming execution.")
	private EventFilter[] all;
	
	@Description("Configures the workflow to wait for any of the defined events before resuming execution.")
	private EventFilter[] any;
	
	@Description("Configures the workflow to wait for the defined event before resuming execution.")
	private EventFilter one;

	public EventFilter[] getAll() {
		return all;
	}

	public void setAll(EventFilter[] all) {
		this.all = all;
	}

	public EventFilter[] getAny() {
		return any;
	}

	public void setAny(EventFilter[] any) {
		this.any = any;
	}

	public EventFilter getOne() {
		return one;
	}

	public void setOne(EventFilter one) {
		this.one = one;
	}
}
