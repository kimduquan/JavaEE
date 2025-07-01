package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines a switch case, encompassing a condition for matching and an associated action to execute upon a match.")
public class SwitchCase {
	
	public static final String DEFAULT = "default";

	@Description("A runtime expression used to determine whether or not the case matches.")
	private String when;
	
	@NotNull
	@Description("The flow directive to execute when the case matches.")
	private String then;

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public String getThen() {
		return then;
	}

	public void setThen(String then) {
		this.then = then;
	}
}
