package azure.devops.services.test;

/**
 * @author PC
 * Reference to test attachment.
 */
public class TestAttachmentReference {

	/**
	 * ID of the attachment.
	 */
	int id;
	/**
	 * Url to download the attachment.
	 */
	String url;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
