package epf.persistence.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
public class TokenBuilder {
	
	/**
	 * 
	 */
	private transient final Token token;
	
	/**
	 * 
	 */
	private transient final PrivateKey privateKey;
	
	/**
	 * 
	 */
	private transient final PublicKey encryptKey;
	
	/**
	 * @param token
	 * @param privateKey
	 * @param encryptKey
	 */
	public TokenBuilder(final Token token, final PrivateKey privateKey, final PublicKey encryptKey) {
		Objects.requireNonNull(token, "Token");
		Objects.requireNonNull(privateKey, "PrivateKey");
		Objects.requireNonNull(encryptKey, "PublicKey");
		this.token = token;
		this.privateKey = privateKey;
		this.encryptKey = encryptKey;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Token build() throws Exception {
		final JwtClaims claims = new JwtClaims();
		claims.setAudience(token.getAudience().stream().collect(Collectors.toList()));
		claims.setExpirationTime(NumericDate.fromSeconds(token.getExpirationTime()));
		claims.setGeneratedJwtId();
		claims.setIssuedAt(NumericDate.fromSeconds(token.getIssuedAtTime()));
		claims.setIssuer(token.getIssuer());
		claims.setNotBefore(NumericDate.fromSeconds(token.getIssuedAtTime()));
		claims.setSubject(token.getSubject());
		claims.setStringClaim(Claims.upn.name(), token.getName());
		claims.setStringListClaim(Claims.groups.name(), token.getGroups().stream().collect(Collectors.toList()));
		for(Entry<? extends String, ? extends Object> entry : token.getClaims().entrySet()) {
			claims.setClaim(entry.getKey(), entry.getValue());
		}
		final JsonWebSignature jws = new JsonWebSignature();
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		jws.setPayload(claims.toJson());
	    jws.setKey(privateKey);
	    final String jwt = jws.getCompactSerialization();
	    token.setRawToken(jwt);
	    token.setTokenID(claims.getJwtId());
	    encryptKey.getAlgorithm();
		return token;
	}
}
