package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;

@Description("Flow Directives are commands within a workflow that dictate its progression.")
public interface FlowDirective {
	
	@Description("Instructs the workflow to proceed with the next task in line. This action may conclude the execution of a particular workflow or branch if there are not task defined after the continue one.")
	String _continue = "continue";
	
	@Description("Halts the current branch's execution, potentially terminating the entire workflow if the current task resides within the main branch.")
	String exit = "exit";
	
	@Description("Provides a graceful conclusion to the workflow execution, signaling its completion explicitly.")
	String end = "end";
	
	@Description("Continues the workflow at the task with the specified name")
	String string = "string";
	
	static boolean isString(final String flowDirective) {
		return !_continue.equals(flowDirective) && !exit.equals(flowDirective) && ! end.equals(flowDirective);
	}
}
