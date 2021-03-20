/**
 * 
 */
package epf.service;

/**
 * @author PC
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param cause
	 */
	public ServiceException(final Throwable cause) {
		super(cause);
	}
}
