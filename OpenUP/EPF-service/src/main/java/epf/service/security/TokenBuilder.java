package epf.service.security;

import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.EPFException;
import epf.client.security.Token;
import epf.schema.roles.Role;
import epf.service.persistence.Credential;

/**
 * @author PC
 *
 */
public class TokenBuilder {

	/**
	 * 
	 */
	private Credential credential;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private long issuedAtTime;
	/**
	 * 
	 */
	private TokenGenerator tokenGenerator;
	/**
	 * 
	 */
	private URL audience;
	/**
	 * 
	 */
	private long expireDuration;
	/**
	 * 
	 */
	private ChronoUnit expireTimeUnit;
	/**
	 * 
	 */
	private final String issuer;
	
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
	public TokenBuilder time(long issuedAtTime) {
		this.issuedAtTime = issuedAtTime;
		return this;
	}

	public TokenBuilder generator(TokenGenerator generator) {
		this.tokenGenerator = generator;
		return this;
	}

	public TokenBuilder url(URL url) {
		this.audience = url;
		return this;
	}

	/**
	 * @param timeUnit
	 * @param duration
	 * @return
	 */
	public TokenBuilder expire(ChronoUnit timeUnit, long duration) {
		this.expireDuration = duration;
		this.expireTimeUnit = timeUnit;
		return this;
	}
	
	/**
     * @param token
     * @param url
     */
    protected static void buildAudience(final Token token, final URL url){
    	final Set<String> aud = new HashSet<>();
        aud.add(String.format(
                Security.AUDIENCE_URL_FORMAT, 
                url.getProtocol(), 
                url.getHost(), 
                url.getPort()));
        token.setAudience(aud);
    }
    
    /**
     * @param username
     * @param time
     * @return
     */
    protected Token buildToken(final String username, final long time) {
    	final Token jwt = new Token();
        jwt.setIssuedAtTime(time);
        jwt.setExpirationTime(time + Duration.of(expireDuration, expireTimeUnit).getSeconds());
        jwt.setIssuer(issuer);
        jwt.setName(username);
        jwt.setSubject(username);
        return jwt;
    }
    
    /**
     * @param token
     * @param userName
     * @param manager
     */
    protected static void buildGroups(final Token token, final String userName, final EntityManager manager) {
    	final Set<String> groups = new HashSet<>();
    	groups.add(Role.DEFAULT_ROLE);
    	token.setGroups(groups);
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

	/**
	 * @return
	 */
	public Token build() {
		Token jwt = buildToken(name, issuedAtTime);
	    buildAudience(jwt, audience);
	    buildGroups(jwt, name, credential.getDefaultManager());
	    try {
			jwt = tokenGenerator.generate(jwt);
		} 
	    catch (Exception e) {
	    	throw new EPFException(e);
		}
	    return jwt;
	}
}
