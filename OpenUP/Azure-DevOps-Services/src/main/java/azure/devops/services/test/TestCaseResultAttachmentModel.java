package azure.devops.services.test;

/**
 * @author PC
 * Test attachment information in a test iteration.
 */
public class TestCaseResultAttachmentModel {

	/**
	 * Path identifier test step in test case workitem.
	 */
	String actionPath;
	/**
	 * Attachment ID.
	 */
	int id;
	/**
	 * Iteration ID.
	 */
	int iterationId;
	/**
	 * Name of attachment.
	 */
	String name;
	/**
	 * Attachment size.
	 */
	int size;
	/**
	 * Url to attachment.
	 */
	String url;
	
	public String getActionPath() {
		return actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIterationId() {
		return iterationId;
	}
	public void setIterationId(int iterationId) {
		this.iterationId = iterationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
