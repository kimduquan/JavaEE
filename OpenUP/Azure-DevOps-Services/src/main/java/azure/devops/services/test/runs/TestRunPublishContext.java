package azure.devops.services.test.runs;

/**
 * @author PC
 * PublishContext of the Runs to be queried.
 */
public class TestRunPublishContext {

	/**
	 * Run is published for any Context.
	 */
	String all;
	/**
	 * Run is published for Build Context.
	 */
	String build;
	/**
	 * Run is published for Release Context.
	 */
	String release;
	
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
}
