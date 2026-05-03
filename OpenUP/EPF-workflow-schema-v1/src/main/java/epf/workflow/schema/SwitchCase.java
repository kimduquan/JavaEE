package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Defines a switch case, encompassing a condition for matching and an associated action to execute upon a match.")
public class SwitchCase {

	@JsonPropertyDescription("A runtime expression used to determine whether or not the case matches.")
	private String when;
	
	@NotNull
	@JsonPropertyDescription("The flow directive to execute when the case matches.")
	private Object then;

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public Object getThen() {
		return then;
	}

	public void setThen(Object then) {
		this.then = then;
	}
}
