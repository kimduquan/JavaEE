package azure.devops.services.test;

public class IdentityRef {

	/**
	 * The descriptor is the primary way to reference the graph subject while the system is running. 
	 * This field will uniquely identify the same graph subject across both Accounts and Organizations.
	 */
	String descriptor;
	/**
	 * Deprecated - Can be retrieved by querying the Graph user referenced in the "self" entry of the IdentityRef "_links" dictionary
	 */
	String directoryAlias;
	/**
	 * This is the non-unique display name of the graph subject. 
	 * To change this field, you must alter its value in the source provider.
	 */
	String displayName;
	/**
	 * 
	 */
	String id;
	/**
	 * Deprecated - Available in the "avatar" entry of the IdentityRef "_links" dictionary
	 */
	String imageUrl;
	/**
	 * Deprecated - Can be retrieved by querying the Graph membership state referenced in the "membershipState" entry of the GraphUser "_links" dictionary
	 */
	boolean inactive;
	/**
	 * Deprecated - Can be inferred from the subject type of the descriptor (Descriptor.IsAadUserType/Descriptor.IsAadGroupType)
	 */
	boolean isAadIdentity;
	/**
	 * Deprecated - Can be inferred from the subject type of the descriptor (Descriptor.IsGroupType)
	 */
	boolean isContainer;
	/**
	 * 
	 */
	boolean isDeletedInOrigin;
	/**
	 * Deprecated - not in use in most preexisting implementations of ToIdentityRef
	 */
	String profileUrl;
	/**
	 * Deprecated - use Domain+PrincipalName instead
	 */
	String uniqueName;
	/**
	 * This url is the full route to the source resource of this graph subject.
	 */
	String url;
	
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public String getDirectoryAlias() {
		return directoryAlias;
	}
	public void setDirectoryAlias(String directoryAlias) {
		this.directoryAlias = directoryAlias;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isInactive() {
		return inactive;
	}
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	public boolean isAadIdentity() {
		return isAadIdentity;
	}
	public void setAadIdentity(boolean isAadIdentity) {
		this.isAadIdentity = isAadIdentity;
	}
	public boolean isContainer() {
		return isContainer;
	}
	public void setContainer(boolean isContainer) {
		this.isContainer = isContainer;
	}
	public boolean isDeletedInOrigin() {
		return isDeletedInOrigin;
	}
	public void setDeletedInOrigin(boolean isDeletedInOrigin) {
		this.isDeletedInOrigin = isDeletedInOrigin;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
