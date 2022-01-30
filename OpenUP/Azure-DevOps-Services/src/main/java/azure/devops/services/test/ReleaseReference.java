package azure.devops.services.test;

/**
 * @author PC
 * Reference to a release.
 */
public class ReleaseReference {

	/**
	 * Number of Release Attempt.
	 */
	int attempt;
	/**
	 * Release Creation Date(UTC).
	 */
	String creationDate;
	/**
	 * Release definition ID.
	 */
	int definitionId;
	/**
	 * Environment creation Date(UTC).
	 */
	String environmentCreationDate;
	/**
	 * Release environment definition ID.
	 */
	int environmentDefinitionId;
	/**
	 * Release environment definition name.
	 */
	String environmentDefinitionName;
	/**
	 * Release environment ID.
	 */
	int environmentId;
	/**
	 * Release environment name.
	 */
	String environmentName;
	/**
	 * Release ID.
	 */
	int id;
	/**
	 * Release name.
	 */
	String name;
	
	public int getAttempt() {
		return attempt;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(int definitionId) {
		this.definitionId = definitionId;
	}
	public String getEnvironmentCreationDate() {
		return environmentCreationDate;
	}
	public void setEnvironmentCreationDate(String environmentCreationDate) {
		this.environmentCreationDate = environmentCreationDate;
	}
	public int getEnvironmentDefinitionId() {
		return environmentDefinitionId;
	}
	public void setEnvironmentDefinitionId(int environmentDefinitionId) {
		this.environmentDefinitionId = environmentDefinitionId;
	}
	public String getEnvironmentDefinitionName() {
		return environmentDefinitionName;
	}
	public void setEnvironmentDefinitionName(String environmentDefinitionName) {
		this.environmentDefinitionName = environmentDefinitionName;
	}
	public int getEnvironmentId() {
		return environmentId;
	}
	public void setEnvironmentId(int environmentId) {
		this.environmentId = environmentId;
	}
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
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
}
