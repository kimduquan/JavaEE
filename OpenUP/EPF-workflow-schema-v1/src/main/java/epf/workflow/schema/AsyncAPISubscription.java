package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Configures a subscription to an AsyncAPI operation.")
public class AsyncAPISubscription {

	@JsonPropertyDescription("A runtime expression, if any, used to filter consumed messages.")
	private String filter;
	
	@NotNull
	@JsonPropertyDescription("An object used to configure the subscription's lifetime.")
	private AsyncAPISubscriptionLifetime consume;
	
	@JsonPropertyDescription("Configures the iterator, if any, for processing each consumed message.")
	private SubscriptionIterator foreach;

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public AsyncAPISubscriptionLifetime getConsume() {
		return consume;
	}

	public void setConsume(AsyncAPISubscriptionLifetime consume) {
		this.consume = consume;
	}

	public SubscriptionIterator getForeach() {
		return foreach;
	}

	public void setForeach(SubscriptionIterator foreach) {
		this.foreach = foreach;
	}
}
