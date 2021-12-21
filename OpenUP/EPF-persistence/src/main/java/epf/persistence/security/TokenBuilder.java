package epf.persistence.security;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.Claims;
import com.ibm.websphere.security.jwt.InvalidClaimException;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.JwtToken;
import epf.security.schema.Token;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class TokenBuilder {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(TokenBuilder.class.getName());
	
	/**
	 * 
	 */
	private transient final Token token;
	
	/**
	 * 
	 */
	private transient final PrivateKey privateKey;
	
	/**
	 * @param token
	 */
	public TokenBuilder(final Token token, final PrivateKey privateKey) {
		Objects.requireNonNull(token, "Token");
		Objects.requireNonNull(privateKey, "PrivateKey");
		this.token = token;
		this.privateKey = privateKey;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Token build() throws Exception {
		final JwtBuilder builder = JwtBuilder.create()
				.audience(token.getAudience()
						.stream()
						.collect(Collectors.toList())
						)
				.issuer(token.getIssuer())
                .subject(token.getSubject())
                .expirationTime(token.getExpirationTime())
                .notBefore(token.getIssuedAtTime())
                .jwtId(true)
                .claim(Claims.iat.name(), token.getIssuedAtTime())
                .claim(Claims.upn.name(), token.getName())
                .claim(Claims.groups.name(), token.getGroups().toArray(new String[token.getGroups().size()]));
		token.getClaims().forEach((claim, value) -> {
        	try {
				builder.claim(claim, value);
			} 
        	catch (InvalidClaimException e) {
        		LOGGER.throwing(getClass().getName(), "build", e);
			}
        });
        builder.signWith("RS256", privateKey);
		final JwtToken jwt = builder.buildJwt();
		final Token newToken = new Token();
		newToken.setAudience(
				jwt
        		.getClaims()
        		.getAudience()
        		.stream()
        		.collect(Collectors.toSet())
        		);
		newToken.setClaims(new HashMap<>(token.getClaims()));
		newToken.setExpirationTime(jwt.getClaims().getExpiration());
		newToken.setGroups(new HashSet<>(token.getGroups()));
		newToken.setIssuedAtTime(jwt.getClaims().getIssuedAt());
		newToken.setIssuer(jwt.getClaims().getIssuer());
		newToken.setName(jwt.getClaims().get(Claims.upn.name()).toString());
		newToken.setSubject(jwt.getClaims().getSubject());
		newToken.setTokenID(jwt.getClaims().getJwtId());
		newToken.setRawToken(jwt.compact());
		return newToken;
	}
}
