/**
 * 
 */
package epf.persistence.security.otp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

import epf.security.schema.Token;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class OTPIdentityStore {

	/**
	 * 
	 */
	private transient final Map<String, Token> tokens = new ConcurrentHashMap<>();
	
	/**
	 * @param token
	 */
	public void putToken(final Token token) {
		tokens.put(token.getTokenID(), token);
	}
	
	/**
	 * @param tokenId
	 * @return
	 */
	public Token removeToken(final String tokenId) {
		return tokens.remove(tokenId);
	}
}
