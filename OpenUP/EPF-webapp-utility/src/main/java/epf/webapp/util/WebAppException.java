package epf.webapp.util;

import epf.util.EPFException;

/**
 * 
 */
public class WebAppException extends EPFException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private final int status;

	/**
	 * @param status
	 * @param cause
	 */
	public WebAppException(final int status, final Throwable cause) {
		super(cause);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
