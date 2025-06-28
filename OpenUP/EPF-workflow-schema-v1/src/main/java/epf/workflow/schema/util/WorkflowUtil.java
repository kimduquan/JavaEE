package epf.workflow.schema.util;

import java.util.UUID;

public interface WorkflowUtil {

	static String getName(final String workflowName, final UUID uuid, final String workflowNamespace, final String taskName) {
		return String.format("%s-%s.%s-%s", workflowName, uuid, workflowNamespace, taskName);
	}
	
	static String getName(final String workflowName, final UUID uuid, final String workflowNamespace) {
		return String.format("%s-%s.%s", workflowName, uuid, workflowNamespace);
	}
}
