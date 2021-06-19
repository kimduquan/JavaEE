/**
 * 
 */
package epf.client.security;

import java.net.URL;
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
     * 
     */
    private URL url;
    
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
	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(final URL url) {
		this.url = url;
	}
}
