package epf.workflow;

import epf.workflow.schema.Workflow;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonValue;

@ApplicationScoped
public class Validator {

	public boolean validate(final JsonValue input, final Workflow workflow) throws Exception {
		return false;
	}
}
