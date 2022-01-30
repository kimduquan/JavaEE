package azure.devops.services.test.runs;

import azure.devops.services.test.ShallowReference;

/**
 * @author PC
 * BuildConfiguration Details.
 */
public class BuildConfiguration {

	/**
	 * Branch name for which build is generated.
	 */
	String branchName;
	/**
	 * BuildDefinitionId for build.
	 */
	int buildDefinitionId;
	/**
	 * Build system.
	 */
	String buildSystem;
	/**
	 * Build Creation Date.
	 */
	String creationDate;
	/**
	 * Build flavor (eg Build/Release).
	 */
	String flavor;
	/**
	 * BuildConfiguration Id.
	 */
	int id;
	/**
	 * Build Number.
	 */
	String number;
	/**
	 * BuildConfiguration Platform.
	 */
	String platform;
	/**
	 * Project associated with this BuildConfiguration.
	 */
	ShallowReference project;
	/**
	 * Repository Guid for the Build.
	 */
	String repositoryGuid;
	/**
	 * Repository Type (eg. TFSGit).
	 */
	String repositoryType;
	/**
	 * Source Version(/first commit) for the build was triggered.
	 */
	String sourceVersion;
	/**
	 * Target BranchName.
	 */
	String targetBranchName;
	/**
	 * Build Uri.
	 */
	String uri;
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getBuildDefinitionId() {
		return buildDefinitionId;
	}
	public void setBuildDefinitionId(int buildDefinitionId) {
		this.buildDefinitionId = buildDefinitionId;
	}
	public String getBuildSystem() {
		return buildSystem;
	}
	public void setBuildSystem(String buildSystem) {
		this.buildSystem = buildSystem;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getFlavor() {
		return flavor;
	}
	public void setFlavor(String flavor) {
		this.flavor = flavor;
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
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public ShallowReference getProject() {
		return project;
	}
	public void setProject(ShallowReference project) {
		this.project = project;
	}
	public String getRepositoryGuid() {
		return repositoryGuid;
	}
	public void setRepositoryGuid(String repositoryGuid) {
		this.repositoryGuid = repositoryGuid;
	}
	public String getRepositoryType() {
		return repositoryType;
	}
	public void setRepositoryType(String repositoryType) {
		this.repositoryType = repositoryType;
	}
	public String getSourceVersion() {
		return sourceVersion;
	}
	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}
	public String getTargetBranchName() {
		return targetBranchName;
	}
	public void setTargetBranchName(String targetBranchName) {
		this.targetBranchName = targetBranchName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
