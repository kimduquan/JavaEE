package azure.devops.services.test;

/**
 * @author PC
 * Reference to shared step workitem.
 */
public class SharedStepModel {

	/**
	 * WorkItem shared step ID.
	 */
	int id;
	/**
	 * Shared step workitem revision.
	 */
	int revision;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
}
