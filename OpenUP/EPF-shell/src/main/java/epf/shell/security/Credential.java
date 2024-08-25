package epf.shell.security;

import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
public class Credential {

	/**
	 * 
	 */
	@Option(names = {"-t", "--token"}, required = true, description = "Token") 
	protected transient String rawToken;
	
    /**
     * 
     */
	@Option(names = {"-tid", "--tokenID"}, required = true, description = "Token ID")  
    protected transient String tokenID;
    
    /**
     * @return
     */
    public String getAuthHeader() {
    	final StringBuilder tokenHeader = new StringBuilder();
    	return tokenHeader.append("Bearer ").append(rawToken).toString();
    }
    
    public String getRawToken() {
    	return rawToken;
    }
    
    public void setRawToken(final String rawToken) {
    	this.rawToken = rawToken;
    }

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(final String tokenID) {
		this.tokenID = tokenID;
	}
}
