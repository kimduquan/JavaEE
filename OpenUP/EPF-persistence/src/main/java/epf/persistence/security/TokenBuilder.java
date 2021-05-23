package epf.persistence.security;

import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.EPFException;
import epf.client.security.Token;
import epf.service.persistence.Credential;
import epf.service.persistence.security.SecurityService;

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
	 * 
	 */
	private transient final SecurityService service;
	
	/**
	 * @param issuer
	 */
	public TokenBuilder(final String issuer, final SecurityService service) {
		this.issuer = issuer;
		this.service = service;
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
        jwt.setGroups(service.getUserRoles(credential.getDefaultManager(), name));
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
