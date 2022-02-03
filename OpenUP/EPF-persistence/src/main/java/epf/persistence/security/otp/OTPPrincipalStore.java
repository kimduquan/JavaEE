package epf.persistence.security.otp;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.persistence.security.TokenIdGenerator;
import epf.security.schema.Token;
import epf.util.MapUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class OTPPrincipalStore {

	/**
	 * 
	 */
	private transient final Map<String, CallerPrincipal> principals = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Token> tokens = new ConcurrentHashMap<>();
	
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
	 * @param principal
	 * @return
	 * @throws Exception 
	 */
	public String putPrincipal(final CallerPrincipal principal) {
		final Token token = newToken(principal.getName());
		principals.put(token.getTokenID(), principal);
		tokens.put(token.getTokenID(), token);
		return token.getTokenID();
	}
	
	/**
	 * @param oneTimePassword
	 * @return
	 */
	public Optional<CallerPrincipal> removePrincipal(final String oneTimePassword) {
		tokens.remove(oneTimePassword);
		return MapUtil.remove(principals, oneTimePassword);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Optional<CallerPrincipal> findPrincipal(final String name){
		return MapUtil.findAny(principals, principal -> principal.getName().equals(name));
	}
}
