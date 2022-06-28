package epf.security.internal.store;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.security.internal.Session;
import epf.security.internal.token.TokenIdGenerator;
import epf.security.schema.Token;
import epf.security.util.Credential;
import epf.util.MapUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class OTPSessionStore {
	
	/**
	 * 
	 */
	private transient final Map<String, Session> sessions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Security.OTP.EXPIRE_DURATION)
	String expireDuration;
	
	/**
	 * @param principal
	 * @return
	 */
	protected Token newToken(final String name) {
		final TokenIdGenerator generator = new  TokenIdGenerator();
    	final Token token = new Token();
    	final long now = Instant.now().getEpochSecond();
    	token.setTokenID(generator.generate());
    	token.setAudience(new HashSet<>());
    	token.setClaims(new HashMap<>());
    	token.setExpirationTime(now + Duration.parse(expireDuration).getSeconds());
    	token.setGroups(new HashSet<>());
    	token.setIssuedAtTime(now);
    	token.setIssuer(Naming.EPF);
    	token.setName(name);
    	token.setSubject(name);
    	return token;
    }
	
	/**
	 * @param credential
	 * @return
	 */
	public String putSession(final Credential credential) {
		final Token token = newToken(credential.getCaller());
		sessions.put(token.getTokenID(), new Session(token, credential));
		return token.getTokenID();
	}
	
	/**
	 * @param oneTimePassword
	 * @return
	 */
	public Optional<Session> removeSession(final String oneTimePassword) {
		return MapUtil.remove(sessions, oneTimePassword);
	}
}
