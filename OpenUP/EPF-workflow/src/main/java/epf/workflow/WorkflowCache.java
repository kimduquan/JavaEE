package epf.workflow;

import java.net.URI;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
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
	private transient Cache<String, WorkflowState> workflowCache;

	@Override
	public HealthCheckResponse call() {
		final CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
		workflowDefinitionCache = cacheManager.getCache(Naming.Workflow.Internal.WORKFLOW_DEFINITION_CACHE);
		if(workflowDefinitionCache == null) {
			final MutableConfiguration<String, WorkflowDefinition> config = new MutableConfiguration<>();
			workflowDefinitionCache = cacheManager.createCache(Naming.Workflow.Internal.WORKFLOW_DEFINITION_CACHE, config);
		}
		workflowCache = cacheManager.getCache(Naming.Workflow.Internal.WORKFLOW_CACHE);
		if(workflowCache == null) {
			final MutableConfiguration<String, WorkflowState> config = new MutableConfiguration<>();
			workflowCache = cacheManager.createCache(Naming.Workflow.Internal.WORKFLOW_CACHE, config);
		}
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
		workflowCache.put(instance.toString(), state);
	}
	
	/**
	 * @param instance
	 * @return
	 */
	public WorkflowState getState(final URI instance) {
		return workflowCache.get(instance.toString());
	}
	
	/**
	 * @param instance
	 * @return
	 */
	public WorkflowState removeState(final URI instance) {
		return workflowCache.getAndRemove(instance.toString());
	}
}
