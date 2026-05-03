package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Flow Directives are commands within a workflow that dictate its progression.")
public enum FlowDirective {

	@JsonProperty("continue")
	@JsonPropertyDescription("Instructs the workflow to proceed with the next task in line. This action may conclude the execution of a particular workflow or branch if there are not task defined after the continue one.")
	continue_,
	@JsonPropertyDescription("Completes the current scope's execution, potentially terminating the entire workflow if the current task resides within the main do scope.")
	exit,
	@JsonPropertyDescription("Provides a graceful conclusion to the workflow execution, signaling its completion explicitly.")
	end,
	
}
