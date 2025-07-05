package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.SubscriptionIterator;
import epf.workflow.schema.Task;

@Description("Provides a mechanism for workflows to await and react to external events, enabling event-driven behavior within workflow systems.")
public class ListenTask extends Task {

	private Listen listen;
	
	@Description("Configures the iterator, if any, for processing each consumed event.")
	private SubscriptionIterator foreach;

	public Listen getListen() {
		return listen;
	}

	public void setListen(Listen listen) {
		this.listen = listen;
	}

	public SubscriptionIterator getForeach() {
		return foreach;
	}

	public void setForeach(SubscriptionIterator foreach) {
		this.foreach = foreach;
	}
}
