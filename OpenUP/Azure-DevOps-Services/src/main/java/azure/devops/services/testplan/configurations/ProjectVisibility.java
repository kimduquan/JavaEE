package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * Project visibility.
 */
public class ProjectVisibility {

	/**
	 * The project is only visible to users with explicit access.
	 */
	String Private;
	/**
	 * The project is visible to all.
	 */
	String Public;
	
	public String getPrivate() {
		return Private;
	}
	public void setPrivate(String private1) {
		Private = private1;
	}
	public String getPublic() {
		return Public;
	}
	public void setPublic(String public1) {
		Public = public1;
	}
}
