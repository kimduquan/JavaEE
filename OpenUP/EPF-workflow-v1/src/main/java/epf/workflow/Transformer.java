package epf.workflow;

import epf.workflow.schema.Input;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonValue;

@ApplicationScoped
public class Transformer {

	public JsonValue transform(final JsonValue data, final Input input) throws Exception {
		return data;
	}
}
