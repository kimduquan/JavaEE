package azure.devops.services.testplan;

/**
 * @author PC
 * Flag to exclude various values from payload. 
 * For example to remove point assignments pass exclude = 1. 
 * To remove extra information (links, test plan , test suite) pass exclude = 2. 
 * To remove both extra information and point assignments pass exclude = 3 (1 + 2).
 */
public class ExcludeFlags {

	/**
	 * To exclude extra information (links, test plan, test suite), pass exclude = 2
	 */
	String extraInformation;
	/**
	 * To exclude nothing
	 */
	String none;
	/**
	 * To exclude point assignments, pass exclude = 1
	 */
	String pointAssignments;
	
	public String getExtraInformation() {
		return extraInformation;
	}
	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}
	public String getNone() {
		return none;
	}
	public void setNone(String none) {
		this.none = none;
	}
	public String getPointAssignments() {
		return pointAssignments;
	}
	public void setPointAssignments(String pointAssignments) {
		this.pointAssignments = pointAssignments;
	}
}
