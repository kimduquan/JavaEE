package epf.persistence.internal.util;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * @author PC
 *
 */
public interface PrincipalUtil {

	/**
	 * @param jwt
	 * @return
	 */
	static Map<String, Object> getClaims(final Principal principal){
		final JsonWebToken jwt = (JsonWebToken)principal;
		final Map<String, Object> claims = new HashMap<>();
		jwt.getClaimNames().forEach(claim -> claims.put(claim, jwt.getClaim(claim)));
		return claims;
	}
}
