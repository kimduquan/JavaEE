package epf.security.internal.token;

import java.net.URL;
import java.security.PrivateKey;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import epf.naming.Naming;
import epf.security.schema.Token;
import epf.util.EPFException;

public class TokenBuilder {
	
	private transient final Token token;
	
	private transient final PrivateKey privateKey;
	
	public TokenBuilder(final Token token, final PrivateKey privateKey) {
		Objects.requireNonNull(token, "Token");
		Objects.requireNonNull(privateKey, "PrivateKey");
		this.token = token;
		this.privateKey = privateKey;
	}

	public Token build() {
		final JwtClaims claims = new JwtClaims();
		claims.setAudience(token.getAudience().stream().collect(Collectors.toList()));
		claims.setExpirationTime(NumericDate.fromSeconds(token.getExpirationTime()));
		claims.setNotBefore(NumericDate.fromSeconds(token.getIssuedAtTime()));
		claims.setGeneratedJwtId();
		claims.setIssuedAt(NumericDate.fromSeconds(token.getIssuedAtTime()));
		claims.setIssuer(token.getIssuer());
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
	    privateKey.getAlgorithm();
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
	
	public static Set<String> buildAudience(
            final URL url,
            final List<String> forwardedHost,
            final Optional<String> tenant){
    	final Set<String> audience = new HashSet<>();
    	if(url != null) {
    		audience.add(String.format("%s://%s/", url.getProtocol(), url.getAuthority()));
    	}
		tenant.ifPresent(audience::add);
		return audience;
    }
    
    public static Map<String, Object> buildClaims(final Map<String, Object> claims, final Optional<String> tenant){
    	final Map<String, Object> newClaims = new HashMap<>(claims);
    	if(tenant.isPresent()) {
        	newClaims.put(Naming.Management.TENANT, tenant);
    	}
    	return newClaims;
    }
}
