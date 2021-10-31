package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * Represents a shallow reference to a TeamProject.
 */
public class TeamProjectReference {

	/**
	 * Project abbreviation.
	 */
	String abbreviation;
	/**
	 * Url to default team identity image.
	 */
	String defaultTeamImageUrl;
	/**
	 * The project's description (if any).
	 */
	String description;
	/**
	 * Project identifier.
	 */
	String id;
	/**
	 * Project last update time.
	 */
	String lastUpdateTime;
	/**
	 * Project name.
	 */
	String name;
	/**
	 * Project revision.
	 */
	int revision;
	/**
	 * Project state.
	 */
	ProjectState state;
	/**
	 * Url to the full version of the object.
	 */
	String url;
	/**
	 * Project visibility.
	 */
	ProjectVisibility visibility;
	
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getDefaultTeamImageUrl() {
		return defaultTeamImageUrl;
	}
	public void setDefaultTeamImageUrl(String defaultTeamImageUrl) {
		this.defaultTeamImageUrl = defaultTeamImageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public ProjectState getState() {
		return state;
	}
	public void setState(ProjectState state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ProjectVisibility getVisibility() {
		return visibility;
	}
	public void setVisibility(ProjectVisibility visibility) {
		this.visibility = visibility;
	}
}
