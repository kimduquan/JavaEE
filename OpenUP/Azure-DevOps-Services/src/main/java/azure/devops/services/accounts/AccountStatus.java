package azure.devops.services.accounts;

/**
 * @author PC
 * Current account status
 */
public enum AccountStatus {
	/**
	 * This account is part of deletion batch and scheduled for deletion.
	 */
	deleted,
	/**
	 * This hosting account is disabled.
	 */
	disabled,
	/**
	 * This hosting account is active and assigned to a customer.
	 */
	enabled,
	/**
	 * This account is not mastered locally and has physically moved.
	 */
	moved,
	/**
	 * 
	 */
	none
}
