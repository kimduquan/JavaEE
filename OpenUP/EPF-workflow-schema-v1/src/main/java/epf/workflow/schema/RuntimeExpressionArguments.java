package epf.workflow.schema;

import java.util.Map;

public class RuntimeExpressionArguments {

	private Map<String, Object> context;
	private Object input;
	private Object output;
	private Map<String, Object> secrets;
	private AuthorizationDescriptor authorization;
	private TaskDescriptor task;
	private WorkflowDescriptor workflow;
	private RuntimeDescriptor runtime;
	
	public Map<String, Object> getContext() {
		return context;
	}
	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	public Object getInput() {
		return input;
	}
	public void setInput(Object input) {
		this.input = input;
	}
	public Object getOutput() {
		return output;
	}
	public void setOutput(Object output) {
		this.output = output;
	}
	public Map<String, Object> getSecrets() {
		return secrets;
	}
	public void setSecrets(Map<String, Object> secrets) {
		this.secrets = secrets;
	}
	public AuthorizationDescriptor getAuthorization() {
		return authorization;
	}
	public void setAuthorization(AuthorizationDescriptor authorization) {
		this.authorization = authorization;
	}
	public TaskDescriptor getTask() {
		return task;
	}
	public void setTask(TaskDescriptor task) {
		this.task = task;
	}
	public WorkflowDescriptor getWorkflow() {
		return workflow;
	}
	public void setWorkflow(WorkflowDescriptor workflow) {
		this.workflow = workflow;
	}
	public RuntimeDescriptor getRuntime() {
		return runtime;
	}
	public void setRuntime(RuntimeDescriptor runtime) {
		this.runtime = runtime;
	}
}
