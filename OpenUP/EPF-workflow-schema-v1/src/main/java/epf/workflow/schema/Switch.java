package epf.workflow.schema;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Enables conditional branching within workflows, allowing them to dynamically select different paths based on specified conditions or criteria")
public class Switch extends Task {

	@JsonProperty("switch")
	@NotNull
	@JsonPropertyDescription("A name/value map of the cases to switch on")
	@JsonbProperty("switch")
	private List<SwitchCase> switch_;

	public List<SwitchCase> getSwitch() {
		return switch_;
	}

	public void setSwitch(List<SwitchCase> switch_) {
		this.switch_ = switch_;
	}
}
