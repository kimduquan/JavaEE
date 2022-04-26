package epf.cache.security;

import javax.cache.Cache;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
public class TokenCache {
	
	/**
	 * 
	 */
	private transient final Cache<String, Object> cache;

	/**
	 * @param cache
	 */
	public TokenCache(final Cache<String, Object> cache) {
		this.cache = cache;
	}

	/**
	 * @param token
	 */
	public void accept(final Token token) {
		cache.put(token.getTokenID(), token);
	}
	
	/**
	 * @param tokenId
	 * @return
	 */
	public Token get(final String tokenId) {
		return (Token) cache.get(tokenId);
	}
}
