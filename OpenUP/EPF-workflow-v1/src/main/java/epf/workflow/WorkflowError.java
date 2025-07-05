package epf.workflow;

public class WorkflowError extends Exception {

	private static final long serialVersionUID = 1L;

	private epf.workflow.schema.Error error;

	public epf.workflow.schema.Error getError() {
		return error;
	}

	public void setError(epf.workflow.schema.Error error) {
		this.error = error;
	}
}
