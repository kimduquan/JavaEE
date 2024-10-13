package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("A task used to set data.")
public class Set {

	@NotNull
	@Description("A name/value mapping of the data to set.")
	private Object set;

	public Object getSet() {
		return set;
	}

	public void setSet(Object set) {
		this.set = set;
	}
}
