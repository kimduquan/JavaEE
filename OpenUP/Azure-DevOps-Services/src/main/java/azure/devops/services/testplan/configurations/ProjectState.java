package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * Project state.
 */
public class ProjectState {

	/**
	 * All projects regardless of state.
	 */
	String all;
	/**
	 * Project has been queued for creation, but the process has not yet started.
	 */
	String createPending;
	/**
	 * Project has been deleted.
	 */
	String deleted;
	/**
	 * Project is in the process of being deleted.
	 */
	String deleting;
	/**
	 * Project is in the process of being created.
	 */
	String New;
	/**
	 * Project has not been changed.
	 */
	String unchanged;
	/**
	 * Project is completely created and ready to use.
	 */
	String wellFormed;
	
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getCreatePending() {
		return createPending;
	}
	public void setCreatePending(String createPending) {
		this.createPending = createPending;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getDeleting() {
		return deleting;
	}
	public void setDeleting(String deleting) {
		this.deleting = deleting;
	}
	public String getNew() {
		return New;
	}
	public void setNew(String new1) {
		New = new1;
	}
	public String getUnchanged() {
		return unchanged;
	}
	public void setUnchanged(String unchanged) {
		this.unchanged = unchanged;
	}
	public String getWellFormed() {
		return wellFormed;
	}
	public void setWellFormed(String wellFormed) {
		this.wellFormed = wellFormed;
	}
}
