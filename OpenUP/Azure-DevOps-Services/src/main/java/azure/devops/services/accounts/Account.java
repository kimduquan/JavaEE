package azure.devops.services.accounts;

/**
 * @author PC
 *
 */
public class Account {

	/**
	 * Identifier for an Account
	 */
	private String accountId;
	/**
	 * Name for an account
	 */
	private String accountName;
	/**
	 * Owner of account
	 */
	private String accountOwner;
	/**
	 * Current account status
	 */
	private AccountStatus accountStatus;
	/**
	 * Type of account: Personal, Organization
	 */
	private AccountType accountType;
	/**
	 * Uri for an account
	 */
	private String accountUri;
	/**
	 * Who created the account
	 */
	private String createdBy;
	/**
	 * Date account was created
	 */
	private String createdDate;
	/**
	 * 
	 */
	private boolean hasMoved;
	/**
	 * Identity of last person to update the account
	 */
	private String lastUpdatedBy;
	/**
	 * Date account was last updated
	 */
	private String lastUpdatedDate;
	/**
	 * Namespace for an account
	 */
	private String namespaceId;
	/**
	 * 
	 */
	private String newCollectionId;
	/**
	 * Organization that created the account
	 */
	private String organizationName;
	/**
	 * Extended properties
	 */
	private PropertiesCollection properties;
	/**
	 * Reason for current status
	 */
	private String statusReason;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountOwner() {
		return accountOwner;
	}
	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public String getAccountUri() {
		return accountUri;
	}
	public void setAccountUri(String accountUri) {
		this.accountUri = accountUri;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public boolean isHasMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getNamespaceId() {
		return namespaceId;
	}
	public void setNamespaceId(String namespaceId) {
		this.namespaceId = namespaceId;
	}
	public String getNewCollectionId() {
		return newCollectionId;
	}
	public void setNewCollectionId(String newCollectionId) {
		this.newCollectionId = newCollectionId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public PropertiesCollection getProperties() {
		return properties;
	}
	public void setProperties(PropertiesCollection properties) {
		this.properties = properties;
	}
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	
}
