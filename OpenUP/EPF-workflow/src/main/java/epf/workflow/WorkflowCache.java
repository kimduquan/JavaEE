package epf.workflow;

import java.net.URI;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.workflow.schema.WorkflowDefinition;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class WorkflowCache implements HealthCheck {
	
	/**
	 * 
	 */
	private static final String KEY_FORMAT = "%s?version=%s";

	/**
	 * 
	 */
	private transient Cache<String, WorkflowDefinition> workflowDefinitionCache;
	
	/**
	 * 
	 */
	private transient Cache<String, WorkflowState> workflowStateCache;

	@Override
	public HealthCheckResponse call() {
		final MutableConfiguration<String, WorkflowDefinition> config = new MutableConfiguration<>();
		workflowDefinitionCache = Caching.getCachingProvider().getCacheManager().createCache("epf_workflow_definition", config);
		final MutableConfiguration<String, WorkflowState> stateConfog = new MutableConfiguration<>();
		workflowStateCache = Caching.getCachingProvider().getCacheManager().createCache("epf_workflow_state", stateConfog);
		return HealthCheckResponse.up("epf-workflow-cache");
	}
	
	/**
	 * @param workflow
	 * @return
	 */
	public WorkflowDefinition get(final String workflow) {
		return workflowDefinitionCache.get(workflow);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	public WorkflowDefinition get(final String workflow, final String version) {
		return workflowDefinitionCache.get(String.format(KEY_FORMAT, workflow, version));
	}
	
	/**
	 * @param workflow
	 * @param workflowDefinition
	 */
	public void put(final String workflow, final WorkflowDefinition workflowDefinition) {
		workflowDefinitionCache.put(workflow, workflowDefinition);
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param workflowDefinition
	 */
	public void put(final String workflow, final String version, final WorkflowDefinition workflowDefinition) {
		workflowDefinitionCache.put(String.format(KEY_FORMAT, workflow, version), workflowDefinition);
	}
	
	/**
	 * @param instance
	 * @param state
	 */
	public void putState(final URI instance, final WorkflowState state) {
		workflowStateCache.put(instance.toString(), state);
	}
	
	/**
	 * @param instance
	 * @return
	 */
	public WorkflowState getState(final URI instance) {
		return workflowStateCache.get(instance.toString());
	}
	
	/**
	 * @param instance
	 * @return
	 */
	public WorkflowState removeState(final URI instance) {
		return workflowStateCache.getAndRemove(instance.toString());
	}
}
