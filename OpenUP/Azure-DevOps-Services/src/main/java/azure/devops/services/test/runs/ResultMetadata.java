package azure.devops.services.test.runs;

/**
 * @author PC
 * ResultMetadata for the given outcome/count.
 */
public class ResultMetadata {

	/**
	 * Flaky metadata
	 */
	String flaky;
	/**
	 * Rerun metadata
	 */
	String rerun;
	
	public String getFlaky() {
		return flaky;
	}
	public void setFlaky(String flaky) {
		this.flaky = flaky;
	}
	public String getRerun() {
		return rerun;
	}
	public void setRerun(String rerun) {
		this.rerun = rerun;
	}
}
