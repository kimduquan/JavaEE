package epf.transaction.schema;

/**
 * 
 */
public interface TransactionSchema {

	/**
	 *
	 */
	String SCHEMA = "EPF_Transaction";
	
	/**
	 *
	 */
	String TRANSACTION = "Transaction";
	
	/**
	 * 
	 */
	interface Query {
		
		/**
		 *
		 */
		String CURRENT_TRANSACTION_ID = "CURRENT_TRANSACTION_ID";
		
		/**
		 *
		 */
		String COMMIT_TRANSACTION = "COMMIT_TRANSACTION";
	}
}
