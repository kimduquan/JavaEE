package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Allows workflows to iterate over a collection of items, executing a defined set of subtasks for each item in the collection. This task type is instrumental in handling scenarios such as batch processing, data transformation, and repetitive operations across datasets.")
public class For {

	public class _For {
		
		@Description("The name of the variable used to store the current item being enumerated.")
		private String each;
		
		@NotNull
		@Description("A runtime expression used to get the collection to enumerate.")
		private String in;
		
		@Description("The name of the variable used to store the index of the current item being enumerated.")
		private String at;

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
	
	@Description("A runtime expression that represents the condition, if any, that must be met for the iteration to continue.")
	private String while_;
	
	@NotNull
	@Description("The task to perform for each item in the collection.")
	private Task do_;

	public String getWhile_() {
		return while_;
	}

	public void setWhile_(String while_) {
		this.while_ = while_;
	}

	public Task getDo_() {
		return do_;
	}

	public void setDo_(Task do_) {
		this.do_ = do_;
	}
}
