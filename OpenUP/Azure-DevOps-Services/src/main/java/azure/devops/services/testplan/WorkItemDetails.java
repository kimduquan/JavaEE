package azure.devops.services.testplan;

/**
 * @author PC
 * Work Item Class
 */
public class WorkItemDetails {

	/**
	 * Work Item Id
	 */
	int id;
	/**
	 * Work Item Name
	 */
	String name;
	/**
	 * Work Item Fields
	 */
	Object[] workItemFields;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object[] getWorkItemFields() {
		return workItemFields;
	}
	public void setWorkItemFields(Object[] workItemFields) {
		this.workItemFields = workItemFields;
	}
}
