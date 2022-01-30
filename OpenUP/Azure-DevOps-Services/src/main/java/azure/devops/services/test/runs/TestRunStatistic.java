package azure.devops.services.test.runs;

import azure.devops.services.test.ShallowReference;

/**
 * @author PC
 * Test run statistics.
 */
public class TestRunStatistic {

	/**
	 * An abstracted reference to some other resource. 
	 * This class is used to provide the build data contracts with a uniform way to reference other resources in a way that provides easy traversal through links.
	 */
	ShallowReference run;
	/**
	 * Test run statistics per outcome.
	 */
	RunStatistic[] runStatistics;
	
	public ShallowReference getRun() {
		return run;
	}
	public void setRun(ShallowReference run) {
		this.run = run;
	}
	public RunStatistic[] getRunStatistics() {
		return runStatistics;
	}
	public void setRunStatistics(RunStatistic[] runStatistics) {
		this.runStatistics = runStatistics;
	}
}
