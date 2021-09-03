/**
 * 
 */
package epf.cache.security;

import javax.cache.Cache;
import epf.cache.ObjectCache;
import epf.client.security.Token;

/**
 * @author PC
 *
 */
public class TokenCache extends ObjectCache<Token> {

	/**
	 * @param cache
	 */
	public TokenCache(final Cache<String, Object> cache) {
		super(cache);
	}

	@Override
	public void accept(final Token token) {
		getCache().put(token.getTokenID(), token);
	}
}
