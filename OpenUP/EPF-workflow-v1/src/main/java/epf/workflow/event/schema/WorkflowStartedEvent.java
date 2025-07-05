package epf.workflow.event.schema;

import java.util.Date;
import epf.workflow.schema.WorkflowDefinitionReference;

public class WorkflowStartedEvent extends WorkflowLifecycleEvent {

	private WorkflowDefinitionReference definition;
	private Date startedAt;
	
	public WorkflowDefinitionReference getDefinition() {
		return definition;
	}
	public void setDefinition(WorkflowDefinitionReference definition) {
		this.definition = definition;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
}
