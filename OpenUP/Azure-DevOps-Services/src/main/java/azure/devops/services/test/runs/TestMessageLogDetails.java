package azure.devops.services.test.runs;

/**
 * @author PC
 * An abstracted reference to some other resource. 
 * This class is used to provide the build data contracts with a uniform way to reference other resources in a way that provides easy traversal through links.
 */
public class TestMessageLogDetails {

	/**
	 * Date when the resource is created
	 */
	String dateCreated;
	/**
	 * Id of the resource
	 */
	int entryId;
	/**
	 * Message of the resource
	 */
	String message;
	
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
