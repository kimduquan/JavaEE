package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.Task;

@Description("Allows workflows to publish events to event brokers or messaging systems, facilitating communication and coordination between different components and services. With the Emit task, workflows can seamlessly integrate with event-driven architectures, enabling real-time processing, event-driven decision-making, and reactive behavior based on incoming events.")
public class EmitTask extends Task {

	private Emit emit;

	public Emit getEmit() {
		return emit;
	}

	public void setEmit(Emit emit) {
		this.emit = emit;
	}
}
