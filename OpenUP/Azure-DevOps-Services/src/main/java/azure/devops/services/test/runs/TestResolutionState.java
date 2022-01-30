package azure.devops.services.test.runs;

import azure.devops.services.test.ShallowReference;

/**
 * @author PC
 * Test Resolution State Details.
 */
public class TestResolutionState {

	/**
	 * Test Resolution state Id.
	 */
	int id;
	/**
	 * Test Resolution State Name.
	 */
	String name;
	/**
	 * An abstracted reference to some other resource. 
	 * This class is used to provide the build data contracts with a uniform way to reference other resources in a way that provides easy traversal through links.
	 */
	ShallowReference project;
	
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
	public ShallowReference getProject() {
		return project;
	}
	public void setProject(ShallowReference project) {
		this.project = project;
	}
}
