package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Allows workflows to iterate over a collection of items, executing a defined set of subtasks for each item in the collection. This task type is instrumental in handling scenarios such as batch processing, data transformation, and repetitive operations across datasets.")
public class For extends Task {

	public class For_ {
		
		@JsonPropertyDescription("The name of the variable used to store the current item being enumerated. Defaults to item.")
		private String each = "item";
		
		@NotNull
		@JsonPropertyDescription("A runtime expression used to get the collection to enumerate.")
		private String in;
		
		@JsonPropertyDescription("The name of the variable used to store the index of the current item being enumerated. Defaults to index.")
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
	
	@JsonProperty("for")
	@JsonbProperty("for")
	private For_ for_;
	
	@JsonProperty("while")
	@JsonPropertyDescription("A runtime expression that represents the condition, if any, that must be met for the iteration to continue.")
	@JsonbProperty("while")
	private String while_;
	
	@NotNull
	@JsonProperty("do")
	@JsonPropertyDescription("The task(s) to perform for each item in the collection.")
	@JsonbProperty("do")
	private Map<String, Task> do_;

	public For_ getFor() {
		return for_;
	}

	public void setFor(For_ for_) {
		this.for_ = for_;
	}

	public String getWhile() {
		return while_;
	}

	public void setWhile(String while_) {
		this.while_ = while_;
	}

	public Map<String, Task> getDo() {
		return do_;
	}

	public void setDo(Map<String, Task> do_) {
		this.do_ = do_;
	}
}
