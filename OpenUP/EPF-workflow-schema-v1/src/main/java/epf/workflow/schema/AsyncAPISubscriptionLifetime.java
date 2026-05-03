package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;

@JsonClassDescription("Configures the lifetime of an AsyncAPI subscription")
public class AsyncAPISubscriptionLifetime {

	@JsonPropertyDescription("The amount of messages to consume. Required if while and until have not been set.")
	private Integer amount;
	
	@JsonProperty("for")
	@JsonPropertyDescription("The duration that defines for how long to consume messages.")
	@JsonbProperty("for")
	private Duration for_;
	
	@JsonProperty("while")
	@JsonPropertyDescription("A runtime expression, if any, used to determine whether or not to keep consuming messages. Required if amount and until have not been set.")
	@JsonbProperty("while")
	private String while_;
	
	@JsonPropertyDescription("A runtime expression, if any, used to determine until when to consume messages. Required if amount and while have not been set.")
	private String until;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Duration getFor() {
		return for_;
	}

	public void setFor(Duration for_) {
		this.for_ = for_;
	}

	public String getWhile() {
		return while_;
	}

	public void setWhile(String while_) {
		this.while_ = while_;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}
}
