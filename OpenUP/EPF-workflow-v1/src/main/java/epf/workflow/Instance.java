package epf.workflow;

import java.net.URI;
import epf.workflow.schema.Workflow;

public class Instance {

	private Workflow workflow;
	private URI id;
	
	public Workflow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	public URI getId() {
		return id;
	}
	public void setId(URI id) {
		this.id = id;
	}
}
