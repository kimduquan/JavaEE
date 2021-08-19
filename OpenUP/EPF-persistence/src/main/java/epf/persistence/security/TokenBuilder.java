package epf.persistence.security;

import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import epf.client.EPFException;
import epf.client.security.Token;

/**
 * @author PC
 *
 */
public class TokenBuilder {
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
	private transient final Set<String> groups;
	
	/**
	 * 
	 */
	private transient final Map<? extends String, ? extends Object> claims;
	
	/**
	 * 
	 */
	private transient boolean createTID = true;

	/**
	 * @param issuer
	 */
	public TokenBuilder(final String issuer, final Set<String> groups, final Map<? extends String, ? extends Object> claims) {
		this.issuer = issuer;
		this.groups = groups;
		this.claims = claims;
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
	 * @param create
	 * @return
	 */
	public TokenBuilder createTID(final boolean create) {
		this.createTID = create;
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
                Security.AUDIENCE_FORMAT, 
                audience.getProtocol(), 
                audience.getHost(), 
                audience.getPort()));
        jwt.setAudience(aud);
        jwt.setGroups(groups);
        jwt.setClaims(claims);
	    try {
			jwt = tokenGenerator.generate(jwt, createTID);
		} 
	    catch (Exception e) {
	    	throw new EPFException(e);
		}
	    return jwt;
	}
}
