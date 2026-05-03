package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;

@JsonClassDescription("Documents the structure - and optionally configures the transformations of - workflow/task output data.")
public class Output {

	@JsonPropertyDescription("The schema used to describe and validate output data.")
	private Schema schema;
	
	@JsonPropertyDescription("A runtime expression, if any, used to filter and/or mutate the workflow/task output.")
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
