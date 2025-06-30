package epf.workflow.schema;

public class ContainerLifetime {
	
	public enum CleanUp {
		always,
		never,
		eventually
	}

	private CleanUp cleanup = CleanUp.never;
	private Duration after;
	
	public CleanUp getCleanup() {
		return cleanup;
	}
	public void setCleanup(CleanUp cleanup) {
		this.cleanup = cleanup;
	}
	public Duration getAfter() {
		return after;
	}
	public void setAfter(Duration after) {
		this.after = after;
	}
}
