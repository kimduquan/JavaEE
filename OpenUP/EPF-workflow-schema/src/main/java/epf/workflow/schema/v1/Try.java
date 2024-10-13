package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Serves as a mechanism within workflows to handle errors gracefully, potentially retrying failed tasks before proceeding with alternate ones.")
public class Try {

	@NotNull
	@Description("The task(s) to perform.")
	private Map<String, Task>[] _try;
	
	@NotNull
	@Description("Configures the errors to catch and how to handle them.")
	private Catch _catch;

	public Map<String, Task>[] get_try() {
		return _try;
	}

	public void set_try(Map<String, Task>[] _try) {
		this._try = _try;
	}

	public Catch get_catch() {
		return _catch;
	}

	public void set_catch(Catch _catch) {
		this._catch = _catch;
	}
}
