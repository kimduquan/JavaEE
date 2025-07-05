package epf.workflow.schema;

import java.util.Map;
import epf.util.json.ext.JsonUtil;

public interface WorkflowUtil {
	
	static String getName(final String workflowName, final String uuid, final String workflowNamespace) {
		return String.format("%s-%s.%s", workflowName, uuid, workflowNamespace);
	}
	
	static String getName(final String workflowName, final String uuid, final String workflowNamespace, final String taskName) {
		return String.format("%s-%s.%s-%s", workflowName, uuid, workflowNamespace, taskName);
	}
	
	static String getName(final Workflow workflow, final WorkflowDescriptor workflowDescriptor) {
		return String.format("%s-%s.%s", workflow.getDocument().getName(), workflowDescriptor.getId(), workflow.getDocument().getNamespace());
	}
	
	static Map<String, Object> toMap(final Object object) throws Error {
		try {
			return JsonUtil.toMap(object);
		}
		catch(Exception ex) {
			throw new Error(ex);
		}
	}
}
