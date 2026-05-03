package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("A task used to set data.")
public class Set extends Task {

	@NotNull
	@JsonPropertyDescription("The data to set. Can be an object or a direct runtime expression.")
	private Either<Map<?, ?>, String> set;

	public Either<Map<?, ?>, String> getSet() {
		return set;
	}

	public void setSet(Either<Map<?, ?>, String> set) {
		this.set = set;
	}
}
