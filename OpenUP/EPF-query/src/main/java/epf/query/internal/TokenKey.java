package epf.query.internal;

import java.io.Serializable;
import epf.security.schema.Token;
import epf.util.security.CryptoUtil;

/**
 * 
 */
public class TokenKey implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	private final String tokenHash;
	
	/**
	 * @param tokenHash
	 */
	private TokenKey(final String tokenHash) {
		this.tokenHash = tokenHash;
	}
	
	@Override
	public String toString() {
		return tokenHash;
	}
	
	public static TokenKey valueOf(final Token token) throws Exception {
		return new TokenKey(CryptoUtil.hash(token.getRawToken()));
	}
	
	/**
	 * @param tokenHash
	 * @return
	 */
	public static TokenKey parseString(final String tokenHash) {
		return new TokenKey(tokenHash);
	}
}
