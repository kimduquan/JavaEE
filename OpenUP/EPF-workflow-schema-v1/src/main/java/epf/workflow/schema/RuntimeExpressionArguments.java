package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class RuntimeExpressionArguments {

	@JsonPropertyDescription("The workflow's context data.")
	private Map<String, Object> context;
	
	@JsonPropertyDescription("The task's transformed input.")
	private Object input;
	
	@JsonPropertyDescription("The task's transformed output.")
	private Object output;
	
	@JsonPropertyDescription("A key/value map of the workflow secrets. To avoid unintentional bleeding, secrets can only be used in the input.from runtime expression.")
	private Map<String, Object> secrets;
	
	@JsonPropertyDescription("Describes the resolved authorization, as defined by the task's authentication, if any.")
	private AuthorizationDescriptor authorization;
	
	@JsonPropertyDescription("Describes the current task.")
	private TaskDescriptor task;
	
	@JsonPropertyDescription("Describes the current workflow.")
	private WorkflowDescriptor workflow;
	
	@JsonPropertyDescription("Describes the runtime.")
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
