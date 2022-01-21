/**
 * 
 */
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
	protected transient String token;
	
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
    	return tokenHeader.append("Bearer ").append(token).toString();
    }
}
