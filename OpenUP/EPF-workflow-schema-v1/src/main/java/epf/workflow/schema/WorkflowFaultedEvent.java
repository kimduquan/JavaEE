package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a workflow has faulted.")
public class WorkflowFaultedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has faulted.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has faulted.")
	private Date faultedAt;
	
	@NotNull
	@JsonPropertyDescription("The error that has cause the workflow to fault.")
	private Error error;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFaultedAt() {
		return faultedAt;
	}

	public void setFaultedAt(Date faultedAt) {
		this.faultedAt = faultedAt;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
