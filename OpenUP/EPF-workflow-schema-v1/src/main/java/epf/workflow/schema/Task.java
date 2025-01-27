package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotBlank;

public class Task {

	@Description("A runtime expression, if any, used to determine whether or not the task should be run. The task is considered skipped if not run, and the raw task input becomes the task's output. The expression is evaluated against the raw task input before any other expression of the task.")
	@NotBlank
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
	@DefaultValue("continue")
	private FlowDirective then = FlowDirective._continue;
	
	@Description("Additional information about the task.")
	private Map<?, ?> metadata;
	
	private Call<?> call;
	
	private Do do_;
	
	private Fork fork;
	
	private Emit emit;
	
	private For for_;
	
	private Listen listen;
	
	private Raise raise;
	
	private Run run;
	
	private Switch switch_;
	
	private Set set;
	
	private Try try_;
	
	private Wait wait;

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

	public Call<?> getCall() {
		return call;
	}

	public void setCall(Call<?> call) {
		this.call = call;
	}

	public Do getDo_() {
		return do_;
	}

	public void setDo_(Do do_) {
		this.do_ = do_;
	}

	public Fork getFork() {
		return fork;
	}

	public void setFork(Fork fork) {
		this.fork = fork;
	}

	public Emit getEmit() {
		return emit;
	}

	public void setEmit(Emit emit) {
		this.emit = emit;
	}

	public For getFor_() {
		return for_;
	}

	public void setFor_(For for_) {
		this.for_ = for_;
	}

	public Listen getListen() {
		return listen;
	}

	public void setListen(Listen listen) {
		this.listen = listen;
	}

	public Raise getRaise() {
		return raise;
	}

	public void setRaise(Raise raise) {
		this.raise = raise;
	}

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public Switch getSwitch_() {
		return switch_;
	}

	public void setSwitch_(Switch switch_) {
		this.switch_ = switch_;
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public Try getTry_() {
		return try_;
	}

	public void setTry_(Try try_) {
		this.try_ = try_;
	}

	public Wait getWait() {
		return wait;
	}

	public void setWait(Wait wait) {
		this.wait = wait;
	}
}
