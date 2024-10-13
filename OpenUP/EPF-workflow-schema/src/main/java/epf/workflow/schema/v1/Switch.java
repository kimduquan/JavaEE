package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Enables conditional branching within workflows, allowing them to dynamically select different paths based on specified conditions or criteria")
public class Switch {

	@NotNull
	@Description("A name/value map of the cases to switch on")
	private SwitchCase[] switch_;

	public SwitchCase[] getSwitch_() {
		return switch_;
	}

	public void setSwitch_(SwitchCase[] switch_) {
		this.switch_ = switch_;
	}
}
