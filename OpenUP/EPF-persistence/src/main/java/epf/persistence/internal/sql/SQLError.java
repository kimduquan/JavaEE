package epf.persistence.internal.sql;

/**
 * @author PC
 *
 */
public enum SQLError {
	ER_ACCESS_DENIED_ERROR("28000", 1045);
	
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
