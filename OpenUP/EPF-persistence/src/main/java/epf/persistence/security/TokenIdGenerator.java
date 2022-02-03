package epf.persistence.security;

import org.jose4j.jwt.JwtClaims;
import epf.util.EPFException;

/**
 * @author PC
 *
 */
public class TokenIdGenerator {

	/**
	 * @return
	 */
	public String generate() {
		final JwtClaims claims = new JwtClaims();
		claims.setGeneratedJwtId();
		try {
			return claims.getJwtId();
		}
		catch (Exception e) {
			throw new EPFException(e);
		}
	}
}
