package epf.security.internal.token;

import org.jose4j.jwt.JwtClaims;
import epf.util.EPFException;

public class TokenIdGenerator {

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
