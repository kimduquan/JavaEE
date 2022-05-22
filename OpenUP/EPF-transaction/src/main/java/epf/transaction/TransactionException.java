package epf.transaction;

/**
 * 
 */
public class TransactionException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param cause
	 */
	public TransactionException(final Throwable cause) {
		super(cause);
	}
}
