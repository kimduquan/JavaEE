package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@Description("A workflow serves as a blueprint outlining the series of tasks required to execute a specific business operation. It details the sequence in which tasks must be completed, guiding users through the process from start to finish, and helps streamline operations, ensure consistency, and optimize efficiency within an organization.")
public class Workflow {

	@NotNull
	@Description("Documents the defined workflow.")
	private Document document;
	
	@Description("Configures the workflow's input.")
	private Input input;
	
	@Description("Defines the workflow's reusable components, if any.")
	private Use use;
	
	@NotNull
	@Description("The task(s) that must be performed by the workflow.")
	private Map<String, Task> do_;
	
	@Description("The configuration, if any, of the workflow's timeout. If a string, must be the name of a timeout defined in the workflow's reusable components.")
	private Either<String, Timeout> timeout;
	
	@Description("Configures the workflow's output.")
	private Output output;
	
	@Description("Configures the workflow's schedule, if any.")
	private Schedule schedule;
	
	@Description("Configures runtime expression evaluation.")
	private Evaluate evaluate;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Use getUse() {
		return use;
	}

	public void setUse(Use use) {
		this.use = use;
	}

	public Map<String, Task> getDo_() {
		return do_;
	}

	public void setDo_(Map<String, Task> do_) {
		this.do_ = do_;
	}

	public Either<String, Timeout> getTimeout() {
		return timeout;
	}

	public void setTimeout(Either<String, Timeout> timeout) {
		this.timeout = timeout;
	}

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Evaluate getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Evaluate evaluate) {
		this.evaluate = evaluate;
	}
}
