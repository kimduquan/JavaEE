package azure.devops.services.test.runs;

/**
 * @author PC
 * Tag attached to a run or result.
 */
public class TestTag {

	/**
	 * Name of the tag, alphanumeric value less than 30 chars
	 */
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
