package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.json.bind.annotation.JsonbProperty;

@JsonClassDescription("A task within a workflow represents a discrete unit of work that contributes to achieving the overall objectives defined by the workflow.")
public class Task {

	@JsonProperty("if")
	@JsonPropertyDescription("A runtime expression, if any, used to determine whether or not the task should be run. The task is considered skipped if not run, and the raw task input becomes the task's output. The expression is evaluated against the raw task input before any other expression of the task.")
	@JsonbProperty("if")
	private String if_;
	
	@JsonPropertyDescription("An object used to customize the task's input and to document its schema, if any.")
	private Input input;
	
	@JsonPropertyDescription("An object used to customize the task's output and to document its schema, if any.")
	private Output output;
	
	@JsonPropertyDescription("An object used to customize the content of the workflow context.")
	private Export export;
	
	@JsonPropertyDescription("The configuration of the task's timeout, if any. If a string, must be the name of a timeout defined in the workflow's reusable components.")
	private Either<String, Timeout> timeout;
	
	@JsonPropertyDescription("The flow directive to execute next. If not set, defaults to continue.")
	private Object then = FlowDirective.continue_;
	
	@JsonPropertyDescription("Additional information about the task.")
	private Map<?, ?> metadata;

	public String getIf() {
		return if_;
	}

	public void setIf(String if_) {
		this.if_ = if_;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public Export getExport() {
		return export;
	}

	public void setExport(Export export) {
		this.export = export;
	}

	public Either<String, Timeout> getTimeout() {
		return timeout;
	}

	public void setTimeout(Either<String, Timeout> timeout) {
		this.timeout = timeout;
	}

	public Object getThen() {
		return then;
	}

	public void setThen(Object then) {
		this.then = then;
	}

	public Map<?, ?> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<?, ?> metadata) {
		this.metadata = metadata;
	}
}
