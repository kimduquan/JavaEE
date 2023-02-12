package epf.workflow;

import epf.workflow.schema.FunctionDefinition;
import epf.workflow.schema.FunctionRefDefinition;

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
	 * @param functionDefinition
	 * @param functionRefDefinition
	 */
	public Function(FunctionDefinition functionDefinition, FunctionRefDefinition functionRefDefinition) {
		this.functionDefinition = functionDefinition;
		this.functionRefDefinition = functionRefDefinition;
	}

	public FunctionDefinition getFunctionDefinition() {
		return functionDefinition;
	}

	public FunctionRefDefinition getFunctionRefDefinition() {
		return functionRefDefinition;
	}
	
	/**
	 * @param workflowInstance
	 * @throws Exception
	 */
	public abstract void invoke(final WorkflowInstance workflowInstance) throws Exception;
}
