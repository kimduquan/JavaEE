package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.v1.util.Either;

public class Task {

	@Description("A runtime expression, if any, used to determine whether or not the task should be run. The task is considered skipped if not run, and the raw task input becomes the task's output. The expression is evaluated against the raw task input before any other expression of the task.")
	private String if_;
	
	@Description("An object used to customize the task's input and to document its schema, if any.")
	private Input input;
	
	@Description("An object used to customize the task's output and to document its schema, if any.")
	private Output output;
	
	@Description("An object used to customize the content of the workflow context.")
	private Export export;
	
	@Description("The configuration of the task's timeout, if any. If a string, must be the name of a timeout defined in the workflow's reusable components.")
	private Either<String, Timeout> timeout;
	
	@Description("The flow directive to execute next. If not set, defaults to continue.")
	private FlowDirective then;
	
	@Description("Additional information about the task.")
	private Map<?, ?> metadata;

	public String getIf_() {
		return if_;
	}

	public void setIf_(String if_) {
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

	public FlowDirective getThen() {
		return then;
	}

	public void setThen(FlowDirective then) {
		this.then = then;
	}

	public Map<?, ?> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<?, ?> metadata) {
		this.metadata = metadata;
	}
}
