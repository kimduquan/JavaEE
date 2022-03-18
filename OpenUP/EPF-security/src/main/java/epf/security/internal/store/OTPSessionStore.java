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
<<<<<<< HEAD:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPPrincipalStore.java
import epf.security.internal.token.TokenIdGenerator;
import epf.security.schema.Token;
=======
import epf.security.internal.Session;
import epf.security.internal.token.TokenIdGenerator;
import epf.security.schema.Token;
import epf.security.util.Credential;
>>>>>>> remotes/origin/native:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPSessionStore.java
import epf.security.util.JPAPrincipal;
import epf.util.MapUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
<<<<<<< HEAD:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPPrincipalStore.java
public class OTPPrincipalStore {
=======
public class OTPSessionStore {
>>>>>>> remotes/origin/native:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPSessionStore.java
	
	/**
	 * 
	 */
<<<<<<< HEAD:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPPrincipalStore.java
	private transient final Map<String, JPAPrincipal> principals = new ConcurrentHashMap<>();
=======
	private transient final Map<String, Session> sessions = new ConcurrentHashMap<>();
>>>>>>> remotes/origin/native:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPSessionStore.java
	
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
	
<<<<<<< HEAD:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPPrincipalStore.java
	/**
	 * @param principal
	 */
	public String putPrincipal(final JPAPrincipal principal) {
		final Token token = newToken(principal.getName());
		principals.put(token.getTokenID(), principal);
=======
	public String putSession(final JPAPrincipal principal, final Credential credential) {
		final Token token = newToken(principal.getName());
		sessions.put(token.getTokenID(), new Session(principal, token, credential));
>>>>>>> remotes/origin/native:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPSessionStore.java
		return token.getTokenID();
	}
	
	/**
	 * @param oneTimePassword
	 * @return
	 */
<<<<<<< HEAD:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPPrincipalStore.java
	public Optional<JPAPrincipal> removePrincipal(final String oneTimePassword) {
		return MapUtil.remove(principals, oneTimePassword);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Optional<JPAPrincipal> findPrincipal(final String name){
		return MapUtil.findAny(principals, principal -> principal.getName().equals(name));
=======
	public Optional<Session> removeSession(final String oneTimePassword) {
		return MapUtil.remove(sessions, oneTimePassword);
>>>>>>> remotes/origin/native:OpenUP/EPF-security/src/main/java/epf/security/internal/store/OTPSessionStore.java
	}
}
