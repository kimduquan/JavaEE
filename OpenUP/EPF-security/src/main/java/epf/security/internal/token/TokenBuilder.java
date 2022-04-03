package epf.security.internal.token;

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
import epf.util.EPFException;

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
	private transient final PublicKey publicKey;
	
	/**
	 * @param token
	 * @param privateKey
	 * @param encryptKey
	 */
	public TokenBuilder(final Token token, final PrivateKey privateKey, final PublicKey publicKey) {
		Objects.requireNonNull(token, "Token");
		Objects.requireNonNull(privateKey, "PrivateKey");
		Objects.requireNonNull(publicKey, "PublicKey");
		this.token = token;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Token build() {
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
		jws.setHeader("typ", "JWT");
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		jws.setPayload(claims.toJson());
	    jws.setKey(privateKey);
	    publicKey.getAlgorithm();
	    try {
			final String jwt = jws.getCompactSerialization();
		    token.setRawToken(jwt);
		    token.setTokenID(claims.getJwtId());
		} 
		catch (Exception e) {
			throw new EPFException(e);
		}
		return token;
	}
}
