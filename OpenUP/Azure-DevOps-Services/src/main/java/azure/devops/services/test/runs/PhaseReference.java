package azure.devops.services.test.runs;

/**
 * @author PC
 * Phase in pipeline
 */
public class PhaseReference {

	/**
	 * Attempt number of the phase
	 */
	int attempt;
	/**
	 * Name of the phase. 
	 * Maximum supported length for name is 256 character.
	 */
	String phaseName;
	
	public int getAttempt() {
		return attempt;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	public String getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
}
