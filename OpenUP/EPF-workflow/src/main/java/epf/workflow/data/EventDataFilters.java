package epf.workflow.data;

import epf.workflow.schema.event.EventDataFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;

/**
 * 
 */
@ApplicationScoped
public class EventDataFilters {
	
	/**
	 * 
	 */
	@Inject
	transient DataMerging dataMerging;
	
	public JsonValue filterEventDataOutput(final EventDataFilter eventDataFilters, final WorkflowData workflowData, final JsonValue eventData) throws Exception {
		JsonValue output = workflowData.getOutput();
		if(eventDataFilters != null && !Boolean.FALSE.equals(eventDataFilters.isUseData())) {
			if(eventDataFilters.getToStateData() != null) {
				output =  dataMerging.mergeStateDataOutput(eventDataFilters.getToStateData(), workflowData.getOutput(), eventData);
			}
			else {
				output = dataMerging.mergeStateDataOutput(workflowData.getOutput(), eventData);
			}
		}
		return output;
	}
}
