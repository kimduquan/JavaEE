package azure.devops.services.test;

/**
 * @author PC
 * WorkItem reference Details.
 */
public class WorkItemReference {

	/**
	 * WorkItem Id.
	 */
	String id;
	/**
	 * WorkItem Name.
	 */
	String name;
	/**
	 * WorkItem Type.
	 */
	String type;
	/**
	 * WorkItem Url. 
	 * Valid Values : (Bug, Task, User Story, Test Case)
	 */
	String url;
	/**
	 * WorkItem WebUrl.
	 */
	String webUrl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
}
