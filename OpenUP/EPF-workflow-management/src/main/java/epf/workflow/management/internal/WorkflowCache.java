package epf.workflow.management.internal;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.workflow.schema.WorkflowDefinition;

@ApplicationScoped
@Readiness
public class WorkflowCache implements HealthCheck {
	
	private static final String KEY_FORMAT = "%s?version=%s";

	private transient Cache<String, WorkflowDefinition> workflowDefinitionCache;

	@Override
	public HealthCheckResponse call() {
		final CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
		workflowDefinitionCache = cacheManager.getCache(Naming.Workflow.Internal.WORKFLOW_DEFINITION_CACHE);
		if(workflowDefinitionCache == null) {
			final MutableConfiguration<String, WorkflowDefinition> config = new MutableConfiguration<>();
			workflowDefinitionCache = cacheManager.createCache(Naming.Workflow.Internal.WORKFLOW_DEFINITION_CACHE, config);
		}
		return HealthCheckResponse.up("epf-workflow-definition-cache");
	}
	
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
