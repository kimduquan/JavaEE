package epf.webapp.security;

/**
 * @author PC
 *
 */
public class Credential {

	/**
	 * 
	 */
	private String caller;
	/**
	 * 
	 */
	private String password;
	
	public String getCaller() {
		return caller;
	}
	public void setCaller(final String caller) {
		this.caller = caller;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
}
