package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines a switch case, encompassing a condition for matching and an associated action to execute upon a match.")
public class SwitchCase {

	@Description("A runtime expression used to determine whether or not the case matches.")
	private String when;
	
	@NotNull
	@Description("The flow directive to execute when the case matches.")
	private FlowDirective then;

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public FlowDirective getThen() {
		return then;
	}

	public void setThen(FlowDirective then) {
		this.then = then;
	}
}
