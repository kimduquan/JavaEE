package epf.service.security;

import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.EPFException;
import epf.client.security.Token;
import epf.schema.h2.QueryNames;
import epf.service.persistence.Credential;

/**
 * @author PC
 *
 */
public class TokenBuilder {

	/**
	 * 
	 */
	private transient Credential credential;
	/**
	 * 
	 */
	private transient String name;
	/**
	 * 
	 */
	private transient long issuedAtTime;
	/**
	 * 
	 */
	private transient TokenGenerator tokenGenerator;
	/**
	 * 
	 */
	private transient URL audience;
	/**
	 * 
	 */
	private transient long expireDuration;
	/**
	 * 
	 */
	private transient ChronoUnit expireTimeUnit;
	/**
	 * 
	 */
	private transient final String issuer;
	
	/**
	 * @param issuer
	 */
	public TokenBuilder(final String issuer) {
		this.issuer = issuer;
	}
	
	/**
	 * @param credential
	 * @return
	 */
	public TokenBuilder fromCredential(final Credential credential) {
		this.credential = credential;
		return this;
	}

	/**
	 * @param name
	 * @return
	 */
	public TokenBuilder userName(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * @param issuedAtTime
	 * @return
	 */
	public TokenBuilder time(final long issuedAtTime) {
		this.issuedAtTime = issuedAtTime;
		return this;
	}

	/**
	 * @param generator
	 * @return
	 */
	public TokenBuilder generator(final TokenGenerator generator) {
		this.tokenGenerator = generator;
		return this;
	}

	/**
	 * @param url
	 * @return
	 */
	public TokenBuilder url(final URL url) {
		this.audience = url;
		return this;
	}

	/**
	 * @param timeUnit
	 * @param duration
	 * @return
	 */
	public TokenBuilder expire(final ChronoUnit timeUnit, final long duration) {
		this.expireDuration = duration;
		this.expireTimeUnit = timeUnit;
		return this;
	}
    
    /**
     * @param token
     * @param userName
     * @param manager
     */
    protected static void buildGroups(final Token token, final String userName, final EntityManager manager) {
    	final Set<String> groups = manager
    			.createNamedQuery(QueryNames.ROLES, String.class)
    			.setParameter(1, userName.toUpperCase(Locale.getDefault()))
    			.setParameter(2, userName.toUpperCase(Locale.getDefault()))
    			.getResultStream()
    			.map(TokenBuilder::mapRole)
    			.collect(Collectors.toSet());
    	token.setGroups(groups);
    }
    
    /**
     * @param role
     * @return
     */
    protected static String mapRole(final String role) {
    	return Stream.of(role.split("_")).map(text -> text.substring(0, 1) + text.substring(1).toLowerCase(Locale.getDefault())).collect(Collectors.joining("_"));
    }

	/**
	 * @return
	 */
	public Token build() {
		Token jwt = new Token();
        jwt.setIssuedAtTime(issuedAtTime);
        jwt.setExpirationTime(issuedAtTime + Duration.of(expireDuration, expireTimeUnit).getSeconds());
        jwt.setIssuer(issuer);
        jwt.setName(name);
        jwt.setSubject(name);
		final Set<String> aud = new HashSet<>();
        aud.add(String.format(
                Security.AUDIENCE_URL_FORMAT, 
                audience.getProtocol(), 
                audience.getHost(), 
                audience.getPort()));
        jwt.setAudience(aud);
	    buildGroups(jwt, name, credential.getDefaultManager());
	    try {
			jwt = tokenGenerator.generate(jwt);
		} 
	    catch (Exception e) {
	    	throw new EPFException(e);
		}
	    return jwt;
	}
    
    /**
     * @param jwt
     * @return
     */
    public Token build(final JsonWebToken jwt){
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
