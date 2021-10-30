package azure.devops.services.test;

/**
 * @author PC
 * Reference to a build.
 */
public class BuildReference {

	/**
	 * Branch name.
	 */
	String branchName;
	/**
	 * Build system.
	 */
	String buildSystem;
	/**
	 * Build Definition ID.
	 */
	int definitionId;
	/**
	 * Build ID.
	 */
	int id;
	/**
	 * Build Number.
	 */
	String number;
	/**
	 * Repository ID.
	 */
	String repositoryId;
	/**
	 * Build URI.
	 */
	String uri;
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBuildSystem() {
		return buildSystem;
	}
	public void setBuildSystem(String buildSystem) {
		this.buildSystem = buildSystem;
	}
	public int getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(int definitionId) {
		this.definitionId = definitionId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
