package azure.devops.services.test;

/**
 * @author PC
 * An abstracted reference to some other resource. 
 * This class is used to provide the build data contracts with a uniform way to reference other resources in a way that provides easy traversal through links.
 */
public class ShallowReference {

	/**
	 * ID of the resource
	 */
	String id;
	/**
	 * Name of the linked resource (definition name, controller name, etc.)
	 */
	String name;
	/**
	 * Full http link to the resource
	 */
	String url;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
