package epf.security.client.jwt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.security.schema.Token;

public interface TokenUtil {
	
	String[] DEFAULT_CLAIMS = new String[] {"aud", "exp", "groups", "iat", "iss", "raw_token", "sub", "jti", "upn", "token_type", "nbf"};

	static Map<String, Object> getClaims(final JsonWebToken jwt){
		final Map<String, Object> claims = new HashMap<>();
		final Set<String> names = new HashSet<>();
		names.addAll(jwt.getClaimNames());
		names.removeAll(Arrays.asList(DEFAULT_CLAIMS));
		names.forEach(name -> {
			final Object value = jwt.getClaim(name);
			claims.put(name, value);
		});
		return claims;
	}
	
	static Token from(final JsonWebToken jwt) {
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
