package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Serves as a mechanism within workflows to handle errors gracefully, potentially retrying failed tasks before proceeding with alternate ones.")
public class Try extends Task {

	@NotNull
	@JsonProperty("try")
	@JsonPropertyDescription("The task(s) to perform.")
	@JsonbProperty("try")
	private Map<String, Task> try_;
	
	@NotNull
	@JsonProperty("catch")
	@JsonPropertyDescription("Configures the errors to catch and how to handle them.")
	@JsonbProperty("catch")
	private Catch catch_;

	public Map<String, Task> getTry() {
		return try_;
	}

	public void setTry(Map<String, Task> try_) {
		this.try_ = try_;
	}

	public Catch getCatch() {
		return catch_;
	}

	public void setCatch(Catch catch_) {
		this.catch_ = catch_;
	}
}
