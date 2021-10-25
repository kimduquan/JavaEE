package azure.devops.services;

public class Approval {

	/**
	 * Identities which are not allowed to approve.
	 */
	private IdentityRef[] blockedApprovers;
	/**
	 * Date on which approval got created.
	 */
	private String createdOn;
	/**
	 * Order in which approvers will be actionable.
	 */
	private ApprovalExecutionOrder executionOrder;
	/**
	 * Unique identifier of the approval.
	 */
	private String id;
	/**
	 * Instructions for the approvers.
	 */
	private String instructions;
	/**
	 * Date on which approval was last modified.
	 */
	private String lastModifiedOn;
	/**
	 * Minimum number of approvers that should approve for the entire approval to be considered approved.
	 */
	private int minRequiredApprovers;
	/**
	 * Current user permissions for approval object.
	 */
	private ApprovalPermissions permissions;
	/**
	 * Overall status of the approval.
	 */
	private ApprovalStatus status;
	/**
	 * List of steps associated with the approval.
	 */
	private ApprovalStep[] steps;
	
	public IdentityRef[] getBlockedApprovers() {
		return blockedApprovers;
	}
	public void setBlockedApprovers(IdentityRef[] blockedApprovers) {
		this.blockedApprovers = blockedApprovers;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public ApprovalExecutionOrder getExecutionOrder() {
		return executionOrder;
	}
	public void setExecutionOrder(ApprovalExecutionOrder executionOrder) {
		this.executionOrder = executionOrder;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public int getMinRequiredApprovers() {
		return minRequiredApprovers;
	}
	public void setMinRequiredApprovers(int minRequiredApprovers) {
		this.minRequiredApprovers = minRequiredApprovers;
	}
	public ApprovalPermissions getPermissions() {
		return permissions;
	}
	public void setPermissions(ApprovalPermissions permissions) {
		this.permissions = permissions;
	}
	public ApprovalStatus getStatus() {
		return status;
	}
	public void setStatus(ApprovalStatus status) {
		this.status = status;
	}
	public ApprovalStep[] getSteps() {
		return steps;
	}
	public void setSteps(ApprovalStep[] steps) {
		this.steps = steps;
	}
}
