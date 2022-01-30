package azure.devops.services.test;

/**
 * @author PC
 *
 */
public class ResultGroupType {

	/**
	 * Hierarchy type of test result.
	 */
	String dataDriven;
	/**
	 * Unknown hierarchy type.
	 */
	String generic;
	/**
	 * Leaf node of test result.
	 */
	String none;
	/**
	 * Hierarchy type of test result.
	 */
	String orderedTest;
	/**
	 * Hierarchy type of test result.
	 */
	String rerun;
	
	public String getDataDriven() {
		return dataDriven;
	}
	public void setDataDriven(String dataDriven) {
		this.dataDriven = dataDriven;
	}
	public String getGeneric() {
		return generic;
	}
	public void setGeneric(String generic) {
		this.generic = generic;
	}
	public String getNone() {
		return none;
	}
	public void setNone(String none) {
		this.none = none;
	}
	public String getOrderedTest() {
		return orderedTest;
	}
	public void setOrderedTest(String orderedTest) {
		this.orderedTest = orderedTest;
	}
	public String getRerun() {
		return rerun;
	}
	public void setRerun(String rerun) {
		this.rerun = rerun;
	}
}
