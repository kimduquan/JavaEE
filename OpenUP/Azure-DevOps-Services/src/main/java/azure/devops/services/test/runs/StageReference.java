package azure.devops.services.test.runs;

/**
 * @author PC
 * Stage in pipeline
 */
public class StageReference {

	/**
	 * Attempt number of stage
	 */
	int attempt;
	/**
	 * Name of the stage. 
	 * Maximum supported length for name is 256 character.
	 */
	String stageName;
	
	public int getAttempt() {
		return attempt;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	
}
