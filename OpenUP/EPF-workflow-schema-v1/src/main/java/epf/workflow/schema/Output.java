package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;

@Description("Documents the structure - and optionally configures the transformations of - workflow/task output data.")
public class Output {

	@Description("The schema used to describe and validate output data.")
	private Schema schema;
	
	@Description("A runtime expression, if any, used to filter and/or mutate the workflow/task output.")
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
