package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;

@Description("Flow Directives are commands within a workflow that dictate its progression.")
public enum FlowDirective {
	@Description("Instructs the workflow to proceed with the next task in line. This action may conclude the execution of a particular workflow or branch if there are not task defined after the continue one.")
	_continue,
	@Description("Halts the current branch's execution, potentially terminating the entire workflow if the current task resides within the main branch.")
	exit,
	@Description("Provides a graceful conclusion to the workflow execution, signaling its completion explicitly.")
	end,
	@Description("Continues the workflow at the task with the specified name")
	string
}
