package epf.workflow.schema;

import java.util.UUID;

public interface Naming {

	static String getName(final String workflowName, final UUID uuid, final String workflowNamespace, final String taskName) {
		return String.format("%s-%s.%s-%s", workflowName, uuid, workflowNamespace, taskName);
	}
}
