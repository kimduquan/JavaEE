package azure.devops.services.test.runs;

/**
 * @author PC
 * Job in pipeline. 
 * This is related to matrixing in YAML.
 */
public class JobReference {

	/**
	 * Attempt number of the job
	 */
	int attempt;
	/**
	 * Matrixing in YAML generates copies of a job with different inputs in matrix. 
	 * JobName is the name of those input. 
	 * Maximum supported length for name is 256 character.
	 */
	String jobName;
	
	public int getAttempt() {
		return attempt;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}
