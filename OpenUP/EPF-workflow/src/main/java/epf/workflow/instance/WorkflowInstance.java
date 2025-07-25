package epf.workflow.instance;

import java.net.URI;
import java.util.Optional;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.nosql.schema.StringOrObject;
import epf.workflow.model.Instance;
import epf.workflow.model.WorkflowData;
import epf.workflow.model.WorkflowState;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.WorkflowDefinition;

@ApplicationScoped
@Readiness
public class WorkflowInstance implements HealthCheck {
	
	private static final String KEY_FORMAT = "%s?version=%s";

	private transient Cache<String, WorkflowDefinition> workflowDefinitionCache;
	
	private transient Cache<String, Instance> workflowCache;

	@Override
	public HealthCheckResponse call() {
		final CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
		workflowDefinitionCache = cacheManager.getCache(Naming.Workflow.Internal.WORKFLOW_DEFINITION_CACHE);
		workflowCache = cacheManager.getCache(Naming.Workflow.Internal.WORKFLOW_CACHE);
		if(workflowCache == null) {
			final MutableConfiguration<String, Instance> config = new MutableConfiguration<>();
			workflowCache = cacheManager.createCache(Naming.Workflow.Internal.WORKFLOW_CACHE, config);
		}
		if(workflowDefinitionCache != null) {
			return HealthCheckResponse.up("epf-workflow-cache");
		}

		return HealthCheckResponse.down("epf-workflow-cache");
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
	
	public WorkflowDefinition getSubWorkflowDefinition(final StringOrObject<SubFlowRefDefinition> subFlowRef) {
		if(subFlowRef.isLeft()) {
			return get(subFlowRef.getLeft());
		}
		else if(subFlowRef.isRight()) {
			final SubFlowRefDefinition subFlowRefDefinition = subFlowRef.getRight();
			return get(subFlowRefDefinition.getWorkflowId(), subFlowRefDefinition.getVersion());
		}
		return null;
	}
	
	public WorkflowDefinition getWorkflowDefinition(final String workflow, final String version) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		if(version != null) {
			workflowDefinition = Optional.ofNullable(get(workflow, version));
		}
		else {
			workflowDefinition = Optional.ofNullable(get(workflow));
		}
		return workflowDefinition.orElseThrow(NotFoundException::new);
	}
	
	public void put(final String workflow, final String version, final WorkflowDefinition workflowDefinition) {
		workflowDefinitionCache.put(String.format(KEY_FORMAT, workflow, version), workflowDefinition);
	}
	
	public void putInstance(final URI instance, final Instance workflowInstance) {
		workflowCache.put(instance.toString(), workflowInstance);
	}
	
	public void replaceInstance(final URI instance, final Instance workflowInstance) {
		workflowCache.replace(instance.toString(), workflowInstance);
	}
	
	public Instance getInstance(final URI instance) {
		return workflowCache.get(instance.toString());
	}
	
	public Instance removeInstance(final URI instance) {
		return workflowCache.getAndRemove(instance.toString());
	}
	
	public void putState(final String state, final URI uri, final WorkflowData workflowData) {
		final Instance instance = getInstance(uri);
		final WorkflowState workflowState = instance.getState();
		final WorkflowState newWorkflowState = new WorkflowState();
		newWorkflowState.setPreviousState(workflowState);
		newWorkflowState.setName(state);
		newWorkflowState.setWorkflowData(workflowData);
		instance.setState(newWorkflowState);
		replaceInstance(uri, instance);
	}
}
