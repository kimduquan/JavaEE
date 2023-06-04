package epf.workflow.function;

import epf.workflow.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;

/**
 * @author PC
 *
 */
public abstract class Function {

	/**
	 * 
	 */
	private final FunctionDefinition functionDefinition;
	
	/**
	 * 
	 */
	private final FunctionRefDefinition functionRefDefinition;
	
	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * @param workflowDefinition
	 * @param functionDefinition
	 * @param functionRefDefinition
	 * @param workflowData
	 */
	public Function(WorkflowDefinition workflowDefinition, FunctionDefinition functionDefinition, FunctionRefDefinition functionRefDefinition, WorkflowData workflowData) {
		this.functionDefinition = functionDefinition;
		this.functionRefDefinition = functionRefDefinition;
		this.workflowDefinition = workflowDefinition;
		this.workflowData = workflowData;
	}

	public FunctionDefinition getFunctionDefinition() {
		return functionDefinition;
	}

	public FunctionRefDefinition getFunctionRefDefinition() {
		return functionRefDefinition;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}
	
	public abstract void invoke() throws Exception;

	public WorkflowData getWorkflowData() {
		return workflowData;
	}
}
