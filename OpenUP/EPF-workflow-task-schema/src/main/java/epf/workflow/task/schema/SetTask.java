package epf.workflow.task.schema;

import java.util.Map;
import epf.workflow.schema.Task;
import epf.workflow.schema.util.Either;

public class SetTask extends Task {

	private Either<String, Map<String, Object>> set;

	public Either<String, Map<String, Object>> getSet() {
		return set;
	}

	public void setSet(Either<String, Map<String, Object>> set) {
		this.set = set;
	}
}
