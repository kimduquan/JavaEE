package epf.security.client;

import java.io.Serializable;
import java.util.Arrays;

public class Credential implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String caller = "";
    
	private char[] password = new char[0];
    
	public String getCaller() {
		return caller;
	}
	
	public void setCaller(final String caller) {
		this.caller = caller;
	}
	
	public char[] getPassword() {
		return Arrays.copyOf(password, password.length);
	}
	
	public void setPassword(final char... password) {
		this.password = Arrays.copyOf(password, password.length);
	}
}
