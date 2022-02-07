package epf.security.internal.sql;

/**
 * @author PC
 *
 */
public enum SQLError {
	WRONG_USER_OR_PASSWORD("28000", 1045),
	NOT_ENOUGH_RIGHTS_FOR("90096", 210);
	
	/**
	 * 
	 */
	private final String state;
	/**
	 * 
	 */
	private final int code;
	
	SQLError(final String state, final int code) {
		this.state = state;
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public int getCode() {
		return code;
	}
}
