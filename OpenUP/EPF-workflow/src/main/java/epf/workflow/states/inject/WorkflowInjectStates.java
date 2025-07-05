package epf.workflow.states.inject;

import epf.util.json.ext.JsonUtil;
import epf.workflow.data.DataMerging;
import epf.workflow.model.WorkflowData;
import epf.workflow.schema.state.InjectState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;

@ApplicationScoped
public class WorkflowInjectStates {
	
	@Inject
	transient DataMerging dataMerging;

	public void inject(final InjectState injectState, final WorkflowData workflowData) throws Exception {
		final JsonValue output = dataMerging.mergeStateDataOutput(workflowData.getOutput(), JsonUtil.toJsonValue(injectState.getData()));
		workflowData.setOutput(output);
	}
}
