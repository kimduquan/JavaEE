/**
 * 
 */
package epf.client.security;

import java.util.Arrays;

/**
 * @author PC
 *
 */
public class Credential {

	/**
	 * 
	 */
	private String caller = "";
    /**
     * 
     */
    private char[] password = new char[0];
    
	/**
	 * @return the caller
	 */
	public String getCaller() {
		return caller;
	}
	/**
	 * @param caller the caller to set
	 */
	public void setCaller(final String caller) {
		this.caller = caller;
	}
	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return Arrays.copyOf(password, password.length);
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(final char... password) {
		this.password = Arrays.copyOf(password, password.length);
	}
}
