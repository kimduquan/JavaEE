package azure.devops.services.test;

/**
 * @author PC
 * Failing since information of a test result.
 */
public class FailingSince {

	/**
	 * Build reference since failing.
	 */
	BuildReference build;
	/**
	 * Time since failing(UTC).
	 */
	String date;
	/**
	 * Release reference since failing.
	 */
	ReleaseReference release;
	
	public BuildReference getBuild() {
		return build;
	}
	public void setBuild(BuildReference build) {
		this.build = build;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ReleaseReference getRelease() {
		return release;
	}
	public void setRelease(ReleaseReference release) {
		this.release = release;
	}
}
