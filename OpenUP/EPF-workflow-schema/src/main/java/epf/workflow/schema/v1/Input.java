package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.v1.util.Either;

@Description("Documents the structure - and optionally configures the transformation of - workflow/task input data.")
public class Input {

	@Description("The schema used to describe and validate raw input data.")
	private Schema schema;
	
	@Description("A runtime expression, if any, used to filter and/or mutate the workflow/task input.")
	private Either<String, Object> from;

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public Either<String, Object> getFrom() {
		return from;
	}

	public void setFrom(Either<String, Object> from) {
		this.from = from;
	}
}
