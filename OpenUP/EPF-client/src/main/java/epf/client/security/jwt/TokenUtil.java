/**
 * 
 */
package epf.client.security.jwt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.security.Token;

/**
 * @author PC
 *
 */
public class TokenUtil {
	
	/**
	 * 
	 */
	public static final String[] DEFAULT_CLAIMS = new String[] {"aud", "exp", "groups", "iat", "iss", "raw_token", "sub", "jti", "upn", "token_type"};

	/**
	 * @param jwt
	 * @return
	 */
	public static Map<String, String> getClaims(final JsonWebToken jwt){
		final Map<String, String> claims = new HashMap<>();
		final Set<String> names = new HashSet<>();
		names.addAll(jwt.getClaimNames());
		names.removeAll(Arrays.asList(DEFAULT_CLAIMS));
		names.forEach(name -> {
			final Object value = jwt.getClaim(name);
			if(value instanceof String) {
				claims.put(name, (String)value);
			}
		});
		return claims;
	}
	
	/**
	 * @param jwt
	 * @return
	 */
	public static Token from(final JsonWebToken jwt) {
		final Token token = new Token();
        token.setAudience(jwt.getAudience());
        token.setExpirationTime(jwt.getExpirationTime());
        token.setGroups(jwt.getGroups());
        token.setIssuedAtTime(jwt.getIssuedAtTime());
        token.setIssuer(jwt.getIssuer());
        token.setName(jwt.getName());
        token.setRawToken(jwt.getRawToken());
        token.setSubject(jwt.getSubject());
        token.setTokenID(jwt.getTokenID());
        return token;
	}
}
