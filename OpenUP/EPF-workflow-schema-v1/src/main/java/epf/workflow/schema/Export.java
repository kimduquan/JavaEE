package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;

@JsonClassDescription("Certain task needs to set the workflow context to save the task output for later usage. Users set the content of the context through a runtime expression. The result of the expression is the new value of the context. The expression is evaluated against the transformed task output.")
public class Export {

	@JsonPropertyDescription("The schema used to describe and validate context. Included to handle the non frequent case in which the context has a known format.")
	private Schema schema;
	
	@JsonPropertyDescription("A runtime expression, if any, used to export the output data to the context.")
	private Either<String, Object> as;

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public Either<String, Object> getAs() {
		return as;
	}

	public void setAs(Either<String, Object> as) {
		this.as = as;
	}
}
