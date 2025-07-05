package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Allows workflows to iterate over a collection of items, executing a defined set of subtasks for each item in the collection. This task type is instrumental in handling scenarios such as batch processing, data transformation, and repetitive operations across datasets.")
public class For {
	
	@Description("The name of the variable used to store the current item being enumerated.")
	@DefaultValue("item")
	private String each = "item";
	
	@NotNull
	@Description("A runtime expression used to get the collection to enumerate.")
	private String in;
	
	@Description("The name of the variable used to store the index of the current item being enumerated.")
	@DefaultValue("index")
	private String at = "index";

	public String getEach() {
		return each;
	}

	public void setEach(String each) {
		this.each = each;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}
}
