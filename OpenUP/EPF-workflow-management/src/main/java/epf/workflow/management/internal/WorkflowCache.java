package epf.workflow.management.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.enterprise.context.ApplicationScoped;
import epf.workflow.schema.WorkflowDefinition;

@ApplicationScoped
public class WorkflowCache {
	
	private static final String KEY_FORMAT = "%s?version=%s";

	private final Map<String, WorkflowDefinition> workflowDefinitionCache = new ConcurrentHashMap<>();
	
	public WorkflowDefinition get(final String workflow) {
		return workflowDefinitionCache.get(workflow);
	}
	
	public WorkflowDefinition get(final String workflow, final String version) {
		return workflowDefinitionCache.get(String.format(KEY_FORMAT, workflow, version));
	}
	
	public void put(final String workflow, final WorkflowDefinition workflowDefinition) {
		workflowDefinitionCache.put(workflow, workflowDefinition);
	}
	
	public void put(final String workflow, final String version, final WorkflowDefinition workflowDefinition) {
		workflowDefinitionCache.put(String.format(KEY_FORMAT, workflow, version), workflowDefinition);
	}
}
